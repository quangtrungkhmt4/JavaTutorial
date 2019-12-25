# Dependency Injection
* tight-coupling(liên kết ràng buộc): trong lập trình hướng đối tượng từ trc đến nay việc một đối tượng được khởi tạo và sử dụng trong một đối tượng khác là hết sức bình thường và điều đó làm cho việc liên kết giữa chúng hết sức chặt chẽ, điều này gây ra việc khi chúng ta thay đổi logic của một class thì class khác cũng sẽ bị ảnh hưởng và việc bảo trì sẽ khó khăn hơn.
* loosely-coupled(giảm sự ràng buộc): là cách chúng ta ám chỉ việc làm giảm sự phụ thuộc giữa các class với nhau.

###### Ví dụ về tight-coupling - liên kết ràng buộc
```java
public class EmailSender{

    public void send(String content){
         // logic send email
    }

}

public class MyApplication{
     
    private EmailSender emailSender = new EmailSender();

    public void sendMessage(String content){
        emailSender.send(content);
    }
}

    /**
     * Ví dụ trên ta thấy về mặt logic hoàn toàn hợp lý và ta thấy sẽ sự ràng buộc giữa hai class này, khi 
     * ta muốn thay đổi phương thức gửi từ Email thành SMS hay bất kỳ phương thức nào thì ta sẽ phải thêm 
     * một class SMSSender nữa và sẽ phải sửa cả trong phương thức send() của class MyApplication. Đặc biệt 
     * là đối tượng EmailSender chỉ tồn tại khi đối tượng MyApplication tồn tại điều này càng thể hiện tính 
     * ràng buộc giữa chúng.
     */
```

###### Ví dụ về loosely-coupled - giảm sự ràng buộc
```java
public interface SenderService{

    public void send(String content);

}


public class EmailSender implements SenderService{

    @Override
    public void send(String content){
         // logic send email
    }

}

public class SMSSender implements SenderService{

    @Override
    public void send(String content){
         // logic send sms
    }

}

public class MyApplication{
     
    private SenderService senderService;

    public MyApplication(SenderService senderService){
        this.senderService = senderService;
    }

    public void sendMessage(String content){
        senderService.send(content);
    }
}

public static void main(String[] args){
    MyApplication appSendEmail = new MyApplication(new EmailSender());
    appSendEmail.send("Content for email");

    MyApplication appSendSMS = new MyApplication(new SMSSender());
    appSendSMS.send("Content for sms");
}

    /**
     * Ví dụ trên ta thấy mối quan hệ giữa 3 class lỏng lẻo hơn rất nhiều, khi ta muốn thay đổi cách thức 
     * gửi là Email hay là SMS thì class MyApplication sẽ không cần quan tâm đến, cách thức gửi sẽ được
     * truyền vào qua hàm khởi tạo trong main. 
     */
```

* Dependency Injection: là một design pattern hay hiểu đơn giản nó là một format lập trình giúp cho ta đạt được hiệu quả cao hơn khi code và giúp cho hệ thống có thể dễ dàng bảo trì hơn.
    
    Các cách để inject dependency vào một đối tượng:
    1. Constructor Injection: inject dependency vào trong hàm khởi tạo.
    2. Setter Injection: sử dụng hàm setter để gán.
    3. Interface injection: dependency sẽ cung cấp một hàm injector để inject nó vào bất kì client nào đc truyền vào. Các client phải implement một interface mà có một setter method dành cho việc nhận dependency.

    Ví dụ trên là ví dụ điển hình sử dụng Dependency Injection.


## Nguồn

* [https://www.journaldev.com/2394/java-dependency-injection-design-pattern-example-tutorial](#https://www.journaldev.com/2394/java-dependency-injection-design-pattern-example-tutorial)
* [https://loda.me/khai-niem-tight-coupling-lien-ket-rang-buoc-va-cach-loosely-coupled-loda1557323622585/](#https://loda.me/khai-niem-tight-coupling-lien-ket-rang-buoc-va-cach-loosely-coupled-loda1557323622585/)