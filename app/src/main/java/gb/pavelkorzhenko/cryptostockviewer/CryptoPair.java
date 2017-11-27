package gb.pavelkorzhenko.cryptostockviewer;

/**
 * Created by small on 11/24/2017.
 */

public class CryptoPair {
    private String name;
    private String description;
    private Double currentExachage;
    private int imgResIdLeft;
    private int imgResIdRight;

    private CryptoPair(String name, String description, Double currentExachage, int imageLeft, int imageRight) {
        this.name = name;
        this.description = description;
        this.currentExachage = currentExachage;
        this.imgResIdLeft = imageLeft;
        this.imgResIdRight = imageRight;
    }

    static final CryptoPair[] cryptopairs = {

            new CryptoPair("bitcoin / litcoin",
                    "bitcoin to litecoin.",
                    0.0345,
                    R.mipmap.btc,
                    R.mipmap.ltc),
            new CryptoPair("bitcoin / usd",
                    "bitcoin to United States dollars.",
                    8123.02,
                    R.mipmap.btc,
                    R.mipmap.usd),
            new CryptoPair("bitcoin / etharium",
                    "bitcoin to etharium",
                    0.0234,
                    R.mipmap.btc,
                    R.mipmap.eth),
            new CryptoPair("litcoin / bitcoin",
                    "litecoin to bitcoin.",
                    340.45,
                    R.mipmap.ltc,
                    R.mipmap.btc),
            new CryptoPair("litcoin / usd",
                    "litecoin to United States dollars.",
                    73.02,
                    R.mipmap.ltc,
                    R.mipmap.usd),
            new CryptoPair("litcoin / etharium",
                    "litecoin to etharium",
                    0.0234,
                    R.mipmap.ltc,
                    R.mipmap.eth),
            new CryptoPair("etharium / bitcoin",
                    "litecoin to bitcoin.",
                    0.3454,
                    R.mipmap.eth,
                    R.mipmap.btc),
            new CryptoPair("etharium / usd",
                    "litecoin to United States dollars.",
                    373.02,
                    R.mipmap.eth,
                    R.mipmap.usd),
            new CryptoPair("etharium / litcoin",
                    "litecoin to etharium",
                    0.5234,
                    R.mipmap.eth,
                    R.mipmap.ltc),
    };

    // getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Double getCurrentExachage() { return currentExachage; }
    public int getImgResIdLeft() { return imgResIdLeft; }
    public int getImgResIdRight() { return imgResIdRight; }


}
