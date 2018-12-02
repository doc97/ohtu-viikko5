package ohtu;

public class TennisGame {

    private static String[] SCORE_NAMES = {
        "Love", "Fifteen", "Thirty", "Forty"
    };
    
    private int m_score1 = 0;
    private int m_score2 = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name))
            m_score1 += 1;
        else
            m_score2 += 1;
    }

    public String getScore() {
        if (m_score1==m_score2)
            return sameScore(m_score1);
        else if (m_score1>=4 || m_score2>=4)
            return twoPointDifferenceBattleScore(m_score1, m_score2);
        else
            return normalScore(m_score1, m_score2);
    }

    private String sameScore(int score) {
        if (score < 0 || score >= SCORE_NAMES.length)
            return "Deuce";
        return SCORE_NAMES[score] + "-All";
    }

    private String twoPointDifferenceBattleScore(int score1, int score2) {
        int diffScore = score1 - score2;
        if (Math.abs(diffScore) == 1)
            return advantage(diffScore > 0);
        else
            return winner(diffScore > 0);
    }

    private String advantage(boolean isPlayer1) {
        return "Advantage " + (isPlayer1 ? player1Name : player2Name);
    }

    private String winner(boolean isPlayer1) {
        return "Win for " + (isPlayer1 ? player1Name : player2Name);
    }

    private String normalScore(int score1, int score2) {
        return SCORE_NAMES[score1] + "-" + SCORE_NAMES[score2];
    }
}