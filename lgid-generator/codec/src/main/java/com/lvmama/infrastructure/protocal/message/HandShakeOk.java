package com.lvmama.infrastructure.protocal.message;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 20:25
 * @Description:
 */
public class HandShakeOk extends OKPacket {

   public  HandShakeOk(int preSeqId,int serverStatus){
        super(preSeqId,serverStatus);
    }
}
