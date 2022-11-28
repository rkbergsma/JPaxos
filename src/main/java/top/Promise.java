package top;

public class Promise {
    private Integer promiseId = null;
    private Integer value = null;

    public Promise(Integer promiseId, Integer value) {
        this.promiseId = promiseId;
        this.value = value;
    }

    public Integer getPromiseId() {
        return promiseId;
    }

    public Integer getValue() {
        return value;
    }
}
