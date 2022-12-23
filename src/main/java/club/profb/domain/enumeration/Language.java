package club.profb.domain.enumeration;

public enum Language {

    EN("en"),
    FA("fa"),
    DE("de");

    private String langKey;

    Language(String langKey) {
        this.langKey = langKey;
    }

    @Override
    public String toString(){
        return this.langKey;
    }

}
