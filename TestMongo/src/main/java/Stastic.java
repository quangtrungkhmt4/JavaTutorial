public class Stastic {
    private double percent;
    private int pointBack;
    private String time;

    public Stastic(double percent, int pointBack, String time) {
        this.percent = percent;
        this.pointBack = pointBack;
        this.time = time;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getPointBack() {
        return pointBack;
    }

    public void setPointBack(int pointBack) {
        this.pointBack = pointBack;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
