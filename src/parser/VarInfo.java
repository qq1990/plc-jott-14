package src.parser;

public class VarInfo {
    public Type type;
    public Boolean initialized;

    public VarInfo(Type type, Boolean initialized) {
        this.type = type;
        this.initialized = initialized;
    }
}
