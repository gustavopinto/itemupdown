package com.my.iud.task;

import com.my.iud.dto.ItemFormBean;
import com.my.iud.entity.Task;
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

public class TaskRecursive extends RecursiveAction {

    private static final long serialVersionUID = 3871790423737400925L;
    private final static Logger logger = LoggerFactory
            .getLogger(TaskRecursive.class);
    private List<Task> taskList = new ArrayList<>();
    private ApplicationContext applicationContext;

    public TaskRecursive(List<Task> taskList,
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
            for (Task task : taskList) {
                long taskId = task.getId();
                int kind = task.getKind();
                int itemType = task.getItemType();
                long sellerId = task.getSellerId();
                User user = new User();
                user.setSellerId(sellerId);

                try {
                    user = userService.getUserBySellerId(user);
                    if (user == null) {
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
                            itemService.itemUpdateDelisting(itemFormBean);
                        } else if (kind == 1) {
                            itemService.itemUpdateListing(itemFormBean);
                        }
                    } else if (itemType == 2) {
                        /**
                         * 出售中的宝贝下架 *
                         */
                        if (kind == 2) {
                            itemService.onsaleItemsDelisting(itemFormBean);
                        } else if (kind == 1) {
                            /**
                             * 出售中的宝贝上架 *
                             */
                            itemService.onsaleItemsUpdateListing(itemFormBean);
                        }
                    } else if (itemType == 3) {
                        /**
                         * 库存中的宝贝 上架 *
                         */
                        if (kind == 1) {
                            itemService.onInventoryItemsListing(itemFormBean);
                        }
                    } else if (itemType == 1) {
                        /**
                         * 全部宝贝上下架 *
                         */
                        if (kind == 1) {
                            itemService.onsaleItemsUpdateListing(itemFormBean);
                            itemService.onInventoryItemsListing(itemFormBean);
                        }
                        if (kind == 2) {
                            itemService.onsaleItemsDelisting(itemFormBean);
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
                    taskService.updateTask(task, user.getId());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage(), e);
                }
                logger.info("卖家" + user.getUserNick() + "的任务##########"
                        + task.getTaskName() + "##########现在已经执行完毕,下次执行时间:"
                        + dateStr);

            }
            return;
        } else {
            List<Task> leftTaskList = taskList.subList(0, taskList.size() / 2);
            List<Task> rightTaskList = taskList.subList(taskList.size() / 2,
                    taskList.size());

            invokeAll(new TaskRecursive(leftTaskList, applicationContext),
                    new TaskRecursive(rightTaskList, applicationContext));
        }

    }
}
