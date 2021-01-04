package in.specialsoft.dhulemall;

public class FeatureContraoller {
        String Orderid;
        int flag;
        String status;
        String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }

    public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static FeatureContraoller getFeatureContraoller() {
            return featureContraoller;
        }

        public static void setFeatureContraoller(FeatureContraoller featureContraoller) {
            FeatureContraoller.featureContraoller = featureContraoller;
        }


        public static FeatureContraoller featureContraoller ;

        public static FeatureContraoller getInstance() {

            if(featureContraoller == null){
                featureContraoller = new FeatureContraoller();
            }
            return featureContraoller ;
        }


}
