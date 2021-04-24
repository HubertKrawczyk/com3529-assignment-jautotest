public class SimpleBankAccount{

  String name;
  int coins;
  boolean vipAccount;

  final int WITHDRAW_COST = 10;

  public SimpleBankAccount(String name, int coins, boolean vipAccount){
    this.name = name;
    this.coins = coins;
    this.vipAccount = vipAccount;
  }

  public boolean withdrawCoins(int withdrawValue) {
    if(withdrawValue < 0){
      return false;
    }
    if(vipAccount) {
      if(this.coins >= withdrawValue) {
        this.coins -= withdrawValue;
        return true;
      } else {
        return false;
      }
    }
    else{
      if(this.coins >= withdrawValue+WITHDRAW_COST) {
        this.coins -= withdrawValue+WITHDRAW_COST;
        return true;
      } else {
        return false;
      }
    }

  }

}