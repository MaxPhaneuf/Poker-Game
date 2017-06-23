/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Frame;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Max
 */
public class PlayerManager {

    public ArrayList<Player> players = new ArrayList<Player>();
    public ArrayList<BigDecimal> playersMoney = new ArrayList<>();
    public int nbrPlayers = 8;
    public int inPlay = 0;
    public int blind = nbrPlayers;
    public JFrame mainWin;
    public Bets bets;
    public Table table;
    public String winner;
    public boolean raising;
    public int time;

    public PlayerManager(JFrame mainWin, Bets bets, Table table) {
        this.mainWin = mainWin;
        this.bets = bets;
        this.table = table;
        firstDraw();

    }

    public Player get(int i) {
        return players.get(i);
    }

    public int size() {
        return players.size();
    }

    public void call(int i) {
        players.get(i).call();
    }

    public void raise(int i) {
        players.get(i).raise();
    }

    public void fold(int i) {
        players.get(i).fold();
    }

    public void startTurn(int i) {
        players.get(i).startTurn();
    }

    public void endTurn(int i) {
        players.get(i).endTurn();
    }

    public void show(int i) {
        players.get(i).show();
    }

    public void hide(int i) {
        players.get(i).hide();
    }

    public void isShown(int i) {
        players.get(i).isShown();
    }

    public void firstDraw() {
        startMoney();
        addPlayers();

    }

    public int firstPlayer() {
        int temp = blind + 1;
        if (temp >= nbrPlayers) {
            temp = 0;
        }
        return temp;
    }

    public void startTurn() {
        endTurn();
        inPlay = firstPlayer();

        while (players.get(inPlay).folded) {
            players.get(inPlay).endTurn();
            inPlay++;
            if (inPlay == players.size()) {
                inPlay = 0;
            }
        }
        players.get(inPlay).startTurn();
        resetRaise();

    }

    public void endTurn() {
        for (Player player : players) {
            player.endTurn();
            player.played = false;
        }
    }

    public void resetRaise() {
        bets.raiseMoney = new BigDecimal(0);
        for (Player player : players) {
            player.raiseMoney = new BigDecimal(0);
        }

        bets.showMoney();

    }

    public void eliminate() {
        for (int i = 0; i < players.size(); i++) {

            if (players.get(i).money.compareTo(BigDecimal.ZERO) == 0) {
                players.get(i).picks.setVisible(false);
                players.remove(i);
                nbrPlayers--;
                i--;
            }
        }
    }

    public void newDraw() {
        endTurn();
        resetPlayers();
        setUpAllHands();
        endDraw();
        blind++;
        if (blind >= nbrPlayers) {
            blind = 0;
        }
        inPlay = firstPlayer();

        startTurn();

    }

    public void endDraw() {
        for (Player player : players) {
            player.folded = false;
            player.blindPaid = false;
        }
    }

    public void addPlayers() {
        Player temp;
        Player next;
        players = new ArrayList<Player>();

        for (int i = 0; i < nbrPlayers; i++) {

            players.add(new Player(mainWin, bets, i));
            players.get(i).setUpHand();
            players.get(i).money = players.get(i).money.add(playersMoney.get(i));
            players.get(i).showMoney();
            players.get(i).picks.setVisible(true);
        }
        for (int i = 0; i < nbrPlayers; i++) {
            if (i < nbrPlayers - 1) {
                players.get(i).nextPlayer = players.get(i + 1);
            }
            if (i > 1) {
                players.get(i).previousPlayer = players.get(i - 1);
            }
            players.get(i).endTurn();
        }
        players.get(players.size() - 1).nextPlayer = players.get(0);
        players.get(0).previousPlayer = players.get(players.size() - 1);
        players.get(firstPlayer()).startTurn();
        players.get(firstPlayer()).raise.setEnabled(true);

    }

    public void startMoney() {
        for (int i = 0; i < nbrPlayers; i++) {
            playersMoney.add(new BigDecimal(1000));
        }
    }

    //In construction
    public void preFlop() {
        players.get(blind - 1).money
                = players.get(firstPlayer()).money.subtract(bets.blindMoney);
        players.get(blind).money
                = players.get(blind).money.subtract(bets.blindMoney);
        bets.blindMoney = bets.blindMoney.add(bets.blindMoney.multiply(new BigDecimal(2)));

    }

    public void showAll() {
        for (Player player : players) {
            if (!player.folded) {
                player.showHand();
            }
        }
    }

    public void resetPlayers() {
        for (Player player : players) {
            player.removeHand();
        }
    }

    public void setUpAllHands() {
        for (Player player : players) {
            player.setUpHand();
        }
    }

    public Player inPlay() {
        return players.get(inPlay);
    }

    public void call() {

        inPlay().call();
        if (!inPlay().error) {
            inPlay().played = true;
            if (inPlay().raising) {
                inPlay().call.setText("Raise");
                inPlay().raising = false;
            }
            nextPlayer();
        }

    }

    public boolean allCalled() {
        boolean temp = false;
        int i = 0;
        do {
            if (players.get(i).folded) {
                temp = true;
            } else {
                temp = players.get(i).raiseMoney.intValue() == bets.raiseMoney.intValue();
            }
            i++;
        } while (temp && i < players.size());
        return temp;
    }

    public boolean allPlayed() {
        boolean temp = false;
        int i = 0;
        do {
            if (players.get(i).folded) {
                temp = true;
            } else {
                temp = players.get(i).played;
            }
            i++;

        } while (temp && i < players.size());
        return temp;

    }

    public void raise() {

        inPlay().raising = true;
        call();

    }

    public void fold() {
        inPlay().fold();
        inPlay().played = true;
        nextPlayer();
    }

    public void nextPlayer() {

        int temp = inPlay + 1;

        while (temp < players.size() && players.get(temp).folded) {
            temp++;
        }
        players.get(inPlay).endTurn();
        inPlay = temp;
        if (inPlay == players.size()) {
            inPlay = 0;
        }

        players.get(inPlay).startTurn();
        if (bets.raiseMoney.intValue() + bets.blindMoney.intValue() >= inPlay().money.intValue()) {
                inPlay().raise.setEnabled(false);
            } else {
                inPlay().raise.setEnabled(true);
        }
    }

    public ArrayList<Results> resultTab() {
        ArrayList<Results> temp = new ArrayList<>();
        for (Player player : players) {
            temp.add(new Results(player.hand, table.draw));
        }
        return temp;
    }

    public ArrayList<Integer> scoreTab(ArrayList<Results> results) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (Results result : results) {
            temp.add(result.getScore());
        }
        return temp;
    }

    public int compareScore(ArrayList<Integer> score) {
        int win = -1;
        int j = 0;
        int i = 1;
        boolean isHigh = false;
        boolean found = false;
        do {
            do {
                if (j != i) {
                    isHigh = score.get(j) > score.get(i);
                } else {
                    isHigh = true;
                }
                i++;

            } while (isHigh && i < score.size());
            found = isHigh;
            if (!found) {
                j++;
                i = 0;
            } else {
                win = j;
            }
        } while (!found && j < (score.size()));

        return win;
    }

    public String twoBest() {
        int temp = players.get(0).score;
        int i = 0;
        int j = 1;
        boolean best = false;
        while (i < players.size()) {

            do {
                best = players.get(i).score > players.get(j).score;

            } while (j < players.size() && best);
        }
        return "";
    }

    public String getWinner(boolean one) {
        int win;
        if (one) {
            win = 0;
        } else {
            win = compareScore(scoreTab(resultTab()));
        }
        if (win != -1) {
            String winner = "";
            winner = "Player " + (players.get(win).playerNbr + 1) + " wins with " + resultTab().get(win).result;
            players.get(win).money = players.get(win).money.add(bets.potMoney);
            bets.potMoney = new BigDecimal(0);
            players.get(win).showMoney();
            bets.showMoney();
        } else {
            winner = "Split Pot";
        }
        return winner;
    }

}
