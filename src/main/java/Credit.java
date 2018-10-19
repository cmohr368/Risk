public class Credit {
    int credit;
    Credit(){
        credit=0;
    }

    public void addCredit(int amount){ credit+=amount; }

    public void spendCredit(int amount){ credit-=amount; }

}
