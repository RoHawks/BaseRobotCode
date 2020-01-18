package logging.interfaces;

public interface ILogDestinCollection { // should this extend ILogDestination or the other way around?

    public void getDestinations(); // should get the destinations and run write for all of them

}