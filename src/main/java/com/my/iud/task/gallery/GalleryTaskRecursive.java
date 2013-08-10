package com.my.iud.task.gallery;

import com.my.iud.dto.ItemFormBean;
import com.my.iud.entity.Gtask;
import com.my.iud.entity.User;
import com.my.iud.service.ItemService;
import com.my.iud.service.TaskService;
import com.my.iud.service.UserService;
import com.my.iud.util.DateUtil;
import com.my.iud.util.StringUtil;
import com.taobao.api.ApiException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class GalleryTaskRecursive extends RecursiveAction {

	private static final long serialVersionUID = 3871790423737400925L;

	private final static Logger logger = LoggerFactory
			.getLogger(GalleryTaskRecursive.class);

	private List<Gtask> taskList = new ArrayList<>();

	private ApplicationContext applicationContext;

	public GalleryTaskRecursive(List<Gtask> taskList,
			ApplicationContext applicationContext) {
		this.taskList = taskList;
		this.applicationContext = applicationContext;
	}

	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		ItemService itemService = (ItemService) applicationContext
				.getBean("itemService");
		UserService userService = (UserService) applicationContext
				.getBean("userService");
		TaskService taskService = (TaskService) applicationContext
				.getBean("taskService");
		if (taskList.size() <= 5) {
			ItemFormBean itemFormBean = new ItemFormBean();

			for (Gtask task : taskList) {
				
				long taskId = task.getId();
				int kind = task.getKind();
				int itemType = task.getItemType();
				long sellerId = task.getSellerId();
				User user = new User();
				user.setSellerId(sellerId);

				try {
					user = userService.getUserBySellerId(user);
                                        if(user == null){
                                            continue;
                                        }
					String sessionKey = user.getSessionKey();
					long uid = user.getId();
					itemFormBean.setSellerId(sellerId);
					itemFormBean.setUid(uid);
					itemFormBean.setSessionKey(sessionKey);
					itemFormBean.setTaskId(taskId);

					if (itemType == 4) {
						String itemString = task.getSelItems();
						String itemArray[] = itemString.split(":");

						itemFormBean.setItemIds(itemArray);
						if (kind == 2) {
							itemService.sItemRecommendDelete(itemFormBean);
						} else if (kind == 1) {
							itemService.sItemRecommend(itemFormBean);
						}
					} else if (itemType == 2) {
						if (kind == 2) {
							itemService.onsaleItemsRecommendDelete(itemFormBean);
						} else if (kind == 1) {
							itemService.onsaleItemsRecommend(itemFormBean);
						}
					}
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
					continue;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
					continue;
				}
				int[] eday = StringUtil.convertStringToIntArray(
						task.getExecDay(), ":");
				int[] etime = StringUtil.convertStringToIntArray(
						task.getExecTime(), ":");
				String dateStr = DateUtil.getNextExecuteDay(eday, etime);
				task.setNexecTime(org.guzz.util.DateUtil.stringToDate(dateStr,
						"yyyy-MM-dd HH:mm:ss"));
				task.setModifyTime(Calendar.getInstance().getTime());
				try {
					taskService.updateGtask(task, user.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
				}
				logger.info("卖家" + user.getUserNick() + "的橱窗推荐任务##########"
						+ task.getTaskName() + "##########现在已经执行完毕,下次执行时间:"
						+ dateStr);

			}
			return;
		} else {
			List<Gtask> leftTaskList = taskList.subList(0, taskList.size() / 2);
			List<Gtask> rightTaskList = taskList.subList(taskList.size() / 2,
					taskList.size());
			invokeAll(new GalleryTaskRecursive(leftTaskList, applicationContext),
					new GalleryTaskRecursive(rightTaskList, applicationContext));
		}

	}

}
