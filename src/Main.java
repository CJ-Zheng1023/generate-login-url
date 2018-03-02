import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.util.Scanner;

public class Main {
    private static final String SURL="http://s.sipo/neusipo/siposearch/eas_security_check?";
    private static String generate(String userId, String an){
        String sysTagCode=URLEncoder.encode("01");       //SysTag

        //加密
        String userIdCodeMI="";
        String anCodeMI="";
        try {
            EncryptForJS encrypt = new EncryptForJS();

            userIdCodeMI = encrypt.getEncString("11112001111200"+userId);
            anCodeMI=encrypt.getEncString(an);


        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String userIdCode=URLEncoder.encode(userIdCodeMI);  //USERID
        String anCode=URLEncoder.encode(anCodeMI);  //AN

        String depNameCode="";

        String depIdCode="";

        String exmNameCode="";


        String easUrl=SURL+"SysTag="+sysTagCode+"&USERID="+userIdCode+"&AN="+anCode+"&DEPNAME="+depNameCode+"&DEPID="+depIdCode+"&EXMNAME="+exmNameCode;
        return easUrl;
    }

    public static void main(String[] args) {
        boolean flag = true;
        while(flag){
            Scanner sc =new Scanner(System.in);
            System.out.println("请输入案卷申请号 :");
            String an = sc.next();
            System.out.println("请输入审查代码 :");
            String userId = sc.next();
            String url = generate(userId, an);
            System.out.println(url);
            System.out.println("是否继续？ （y/n）");
            String f = sc.next();
            if(!f.equals("y")){
                System.out.println("欢迎再次使用！");
                flag = false;
            }
        }

    }
}
