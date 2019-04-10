package takeout.yummy.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import takeout.yummy.dao.OrderDetailRepository;
import takeout.yummy.dao.OrderListRepository;
import takeout.yummy.dao.ProductInfoRepository;
import takeout.yummy.entity.OrderDetail;
import takeout.yummy.entity.OrderList;
import takeout.yummy.entity.ProductInfo;

import java.util.List;
import java.util.TimerTask;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/16
 */
public class PayDDLTask extends TimerTask {


    private Long oid;

    private OrderListRepository orderListRepository;
    private ProductInfoRepository productInfoRepository;
    private OrderDetailRepository orderDetailRepository;

    public PayDDLTask(Long oid, OrderListRepository orderListRepository,
                      ProductInfoRepository productInfoRepository,OrderDetailRepository orderDetailRepository) {
        this.oid = oid;
        this.orderListRepository = orderListRepository;
        this.productInfoRepository = productInfoRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public void run() {
        OrderList orderList = orderListRepository.findByOid(oid).get();
        if(!orderList.isPaid()){
            orderList.setValid(false);
            orderListRepository.save(orderList);
            // 加库存
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderListOid(oid);
            for(int i=0;i<orderDetailList.size();i++){
                OrderDetail orderDetail = orderDetailList.get(i);
                ProductInfo productInfo = orderDetail.getProductInfo();
                productInfo.setQuantity(productInfo.getQuantity()+orderDetail.getQuantity());
                productInfoRepository.save(productInfo);
            }
        }
        System.out.println("Pay DDL Task!");
    }
}
