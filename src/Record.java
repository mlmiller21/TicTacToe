public class Record {

    private String config;
    private int score;

    /**
     * Return a new record with specified configurations and score
     * @param config
     * @param score
     */
    public Record(String config, int score){
        this.config = config;
        this.score = score;
    }

    /**
     * String configuration stored in Record, represents record for game tree
     * @return configuration in record
     */
    public String getConfig(){
        return config;
    }

    /**
     * Score stored in Record, represents score for game tree
     * @return score in Record
     */
    public int getScore(){
        return score;
    }
}
