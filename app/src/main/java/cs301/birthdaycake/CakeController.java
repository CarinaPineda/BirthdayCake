package cs301.birthdaycake;

public class CakeController {
    private CakeView cv;
    private CakeModel cm;

    public CakeController(CakeView cv){
        this.cv = cv;
        this.cm = cv.getCakeModel();
    }
}
