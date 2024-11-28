package model;

public class forgetpassword {
    private String otp;

   private static forgetpassword instanceforgetpassword = null;

    public static forgetpassword getinstance(){
        if(instanceforgetpassword == null){
            instanceforgetpassword = new forgetpassword();
        }
        return instanceforgetpassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
