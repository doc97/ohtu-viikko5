
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0)
            throw new IllegalArgumentException("kapasiteetti ei saa olla negatiivinen");
        if (kasvatuskoko < 0)
            throw new IllegalArgumentException("kasvatuskoko ei saa olla negatiivinen");
        ljono = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (kuuluu(luku))
            return false;

        ljono[alkioidenLkm++] = luku;
        if (alkioidenLkm % ljono.length == 0)
            kasvata();
        return true;
    }

    public boolean kuuluu(int luku) {
        return indeksi(luku) >= 0;
    }

    public boolean poista(int luku) {
        int kohta = indeksi(luku);
        if (kohta != -1) {
            alkioidenLkm--;
            System.arraycopy(ljono, kohta + 1, ljono, kohta, alkioidenLkm - kohta);
            return true;
        }
        return false;
    }

    private int indeksi(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i])
                return i;
        }
        return -1;
    }

    private void kasvata() {
        int[] uusi = new int[alkioidenLkm + kasvatuskoko];
        System.arraycopy(ljono, 0, uusi, 0, alkioidenLkm);
        ljono = uusi;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + ljono[0] + "}";
        } else {
            StringBuilder tuotos = new StringBuilder("{");
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos.append(ljono[i]);
                tuotos.append(", ");
            }
            tuotos.append(ljono[alkioidenLkm - 1]);
            tuotos.append("}");
            return tuotos.toString();
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        System.arraycopy(ljono, 0, taulu, 0, taulu.length);
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        return operattori(a, b, (aTaulu, bTaulu, tulos) -> {
            for (int aArvo : aTaulu) tulos.lisaa(aArvo);
            for (int bArvo : bTaulu) tulos.lisaa(bArvo);
        });
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        return operattori(a, b, (aTaulu, bTaulu, tulos) -> {
            for (int aArvo : aTaulu) {
                for (int bArvo : bTaulu) {
                    if (aArvo == bArvo) {
                        tulos.lisaa(aArvo);
                        break;
                    }
                }
            }
        });
    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        return operattori(a, b, (aTaulu, bTaulu, tulos) -> {
            for (int i : aTaulu)
                tulos.lisaa(i);
            for (int i = 0; i < bTaulu.length; i++)
                tulos.poista(i);
        });
    }

    private static IntJoukko operattori(IntJoukko a, IntJoukko b, IntJoukkoOperaatio operaatio) {
        IntJoukko tulos = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        operaatio.operator(aTaulu, bTaulu, tulos);
        return tulos;
    }

    private interface IntJoukkoOperaatio {
        void operator(int[] a, int[] b, IntJoukko tulos);
    }
}