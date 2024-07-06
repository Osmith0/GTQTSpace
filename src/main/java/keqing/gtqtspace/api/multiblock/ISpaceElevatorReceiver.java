package keqing.gtqtspace.api.multiblock;

public interface ISpaceElevatorReceiver {

    ISpaceElevatorProvider getSpaceElevator();

    void setSpaceElevator(ISpaceElevatorProvider provider);

    void sentWorkingDisabled();

    void sentWorkingEnabled();

    String getNameForDisplayCount();
}
