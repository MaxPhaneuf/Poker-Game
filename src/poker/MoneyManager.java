/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.math.BigDecimal;

/**
 *
 * @author Max
 */
public class MoneyManager {

    Player player;
    Bets bets;
    public BigDecimal money;
    public BigDecimal raiseMoney = new BigDecimal(0);
    public boolean blindPaid;

    public MoneyManager(Player player, Bets bets) {
        this.player = player;
        this.bets = bets;
    }

    public void call() {
        BigDecimal blind = new BigDecimal(0);
        BigDecimal temp = bets.raiseMoney;

        if (!blindPaid) {
            blind = payBlind(blind);
        }
        if (player.raising) {
            raise();
            temp = temp.add(raiseMoney);

        }
        if (temp.intValue() <= money.intValue()) {
            money = money.subtract(temp.add(blind));
            bets.raiseMoney = temp;
            bets.potMoney = bets.potMoney.add(temp.add(blind));
            player.showMoney();
            bets.showMoney();
            if (player.nextPlayer != null) {
                    player.nextPlayer.startTurn();
            }
            player.endTurn();
        }
             
    }

    public void raise() {
        BigDecimal temp = bets.raiseMoney.add(new BigDecimal(parse()));
        raiseMoney = temp;
    }

    private BigDecimal payBlind(BigDecimal temp) {
        blindPaid = true;
        temp = temp.add(bets.blindMoney);
        return temp;

    }

    public String parse() {
        String temp = player.raiseBox.getText();
        if (!temp.isEmpty()) {
            int i = temp.indexOf("$");
            if (i > -1) {
                temp = temp.substring(0, i);
            }
        } else {
            temp = "0";
        }

        return temp;

    }

    @Override
    public String toString() {
        return money.toString() + "$";
    }
}
