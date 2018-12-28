/*
package com.test.springboot.controller.task;


import com.test.springboot.controller.springboot.BaseTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

*/
/**
 * @author wwy
 * @Description 阿里支付宝轮询
 * @date 2018年9月30日 上午10:41:31
 *//*

@Component
@EnableScheduling
public class AliPayReacherTask extends BaseTask {

    private static final Logger LOGGER = LogManager.getLogger(AliPayReacherTask.class);

   */
/* @Autowired
    private IAccountRecharge accountRechargeService;*//*


    //@Scheduled(cron = "0 0/5 1 * * ?") // 秒 分 时 日 月 星期几
    @Scheduled(cron = "30 * * * * ?") // 秒 分 时 日 月 星期几
    public void run() {
        super.run();
    }

    @Override
    protected void setThreadName() {
        Thread.currentThread().setName(this.getClass().getSimpleName());
    }

    */
/**
     * 任务具体实现方法
     * 	交易状态：
     * 	WAIT_BUYER_PAY（交易创建，等待买家付款）、
     * 	TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
     * 	TRADE_SUCCESS（交易支付成功）、
     * 	TRADE_FINISHED（交易结束，不可退款）
     *//*

    public void doTask() {
       */
/* AccountRecharge accountRCondi = new AccountRecharge();
        accountRCondi.setStatus(EnumAccountRecharge.DEALING.getStatusCode());
        String format = "yyyy-MM-dd";
        String yestoday = DateUtil.getYesterdayTime(new Date(), format);
        Date thirtyMin = DateUtil.rollMinute(new Date(), -30);
        accountRCondi.and(Expressions.ge("add_time", yestoday));
        accountRCondi.and(Expressions.lt("add_time", thirtyMin));
        accountRCondi.setPageNumber(5);
        List<AccountRecharge> accountRecharges = accountRechargeService.getList(accountRCondi);
        if (accountRecharges != null&& accountRecharges.size()>0) {
            LOGGER.info("===处理中充值交易数量：==="+accountRecharges.size());
            for (AccountRecharge accountRecharge : accountRecharges) {
                try {
                    AlipayTradeQueryResponse response = query(accountRecharge);
                    String body = response.getBody();
                    JSONObject result = JSONObject.parseObject(body);
                    JSONObject responseJson = result.getJSONObject("alipay_trade_query_response");
                    String outTradeNo = responseJson.getString("out_trade_no");
                    String code = response.getSubCode();
                    AliPayReturnDto dto = new AliPayReturnDto();
                    //订单不存在
                    if("ACQ.TRADE_NOT_EXIST".equals(code)){
                       dto.setOutTradeNo(outTradeNo);
                       dto.setCode(code);
                       accountRechargeService.processOnRechargeNotExist(dto);
                       continue;
                    }


                    if(response.isSuccess()){
                        String totalAmount = responseJson.getString("total_amount");
                        String tradeStatus = responseJson.getString("trade_status");
                        String tradeNo = responseJson.getString("trade_no");

                        switch (tradeStatus) {
                            case "TRADE_SUCCESS"://充值成功
                                dto.setOutTradeNo(outTradeNo);
                                dto.setAliTradeNo(tradeNo);
                                dto.setTotalAmount(totalAmount);
                                accountRechargeService.processOnRechargeSuccess(dto);
                                break;
                            case "TRADE_CLOSED"://订单失效
                                dto.setOutTradeNo(outTradeNo);
                                dto.setAliTradeNo(tradeNo);
                                accountRechargeService.processOnRechargeClose(dto);
                                break;
                        }
                    }

                } catch (AlipayApiException e) {
                    e.printStackTrace();
                }
            }

        }
*//*

    }


   */
/* private AlipayTradeQueryResponse query(AccountRecharge accountRecharge) throws AlipayApiException {
        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        AlipayClient alipayClient = AliPayUtil.getAlipayClient();

        //商户订单号，商户网站订单系统中唯一订单号
         String outTradeNo = accountRecharge.getTradeNo();
         String aliTradeNo = accountRecharge.getAliTradeNo();
        //请二选一设置

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","+"\"trade_no\":\""+ aliTradeNo +"\"}");

        //请求
        AlipayTradeQueryResponse result = alipayClient.execute(alipayRequest);
        return result;
    }*//*



}*/
