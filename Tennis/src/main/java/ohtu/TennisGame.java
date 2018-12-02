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
        if (playerName.equals("player1"))
            m_score1 += 1;
        else
            m_score2 += 1;
    }

    public String getScore() {
        if (m_score1==m_score2) {
            return sameScore(m_score1);
        } else if (m_score1>=4 || m_score2>=4) {
            return tieScore(m_score1, m_score2);
        } else {
            return normalScore(m_score1, m_score2);
        }
    }

    private String sameScore(int score) {
        if (score < 0 || score >= SCORE_NAMES.length)
            return "Deuce";
        return SCORE_NAMES[score] + "-All";
    }

    private String tieScore(int score1, int score2) {
        int minusResult = score1 - score2;
        if (minusResult==1)
            return "Advantage player1";
        else if (minusResult ==-1)
            return "Advantage player2";
        else if (minusResult>=2)
            return "Win for player1";
        else
            return "Win for player2";
    }

    private String normalScore(int score1, int score2) {
        StringBuilder result = new StringBuilder();
        int tempScore;
        for (int i = 1; i < 3; i++) {
            if (i == 1) {
                tempScore = score1;
            } else {
                result.append("-");
                tempScore = score2;
            }

            switch(tempScore) {
                case 0:
                    result.append("Love");
                    break;
                case 1:
                    result.append("Fifteen");
                    break;
                case 2:
                    result.append("Thirty");
                    break;
                case 3:
                    result.append("Forty");
                    break;
            }
        }
        return result.toString();
    }
}