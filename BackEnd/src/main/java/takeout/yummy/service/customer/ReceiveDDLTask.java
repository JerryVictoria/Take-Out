package takeout.yummy.service.customer;

import takeout.yummy.dao.OrderListRepository;
import takeout.yummy.entity.OrderList;

import java.util.TimerTask;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/3/1
 */
public class ReceiveDDLTask extends TimerTask{

    private OrderList orderList;
    private OrderListRepository orderListRepository;

    public ReceiveDDLTask(OrderList orderList, OrderListRepository orderListRepository){
        this.orderList = orderList;
        this.orderListRepository = orderListRepository;
    }

    @Override
    public void run() {
        orderList.setReceived(true);
        orderListRepository.save(orderList);
    }
}
