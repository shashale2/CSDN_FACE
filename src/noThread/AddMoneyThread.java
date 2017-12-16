package noThread;

public class AddMoneyThread implements Runnable {
    private Account account;    // 存入账户
    private double money;       // 存入金额

    public AddMoneyThread(Account account, double money) {
        this.account = account;
        this.money = money;
    }

    public void run() {
    	synchronized (account) {
            account.deposit(money); 
            System.out.println(account.getBalance());
        }
    }

}
