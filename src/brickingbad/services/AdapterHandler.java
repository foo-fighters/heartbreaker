package brickingbad.services;


public class AdapterHandler {

  private static Adapter currentAdapter;

  public static void setCurrentAdapter(Adapter adapter) {
    System.out.println("Adapted services to: " + adapter);
    currentAdapter = adapter;
  }

  public static Adapter getCurrentAdapter() {
    return currentAdapter;
  }

}
