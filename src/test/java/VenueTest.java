import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_instantiatesCorrectly_true() {
    Venue myVenue = new Venue("Mow the lawn");
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
  public void getDescription_venueInstantiatesWithDescription_String() {
    Venue myVenue = new Venue("Mow the lawn");
    assertEquals("Mow the lawn", myVenue.getDescription());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame_true() {
    Venue firstVenue = new Venue("Mow the lawn");
    Venue secondVenue = new Venue("Mow the lawn");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(myVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findsVenueInDatabase_true() {
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void update_updatesVenueDescription_true() {
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    myVenue.update("Take a nap");
    assertEquals("Take a nap", Venue.find(myVenue.getId()).getDescription());
  }

  @Test
  public void delete_deletesVenue_true() {
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    int myVenueId = myVenue.getId();
    myVenue.delete();
    assertEquals(null, Venue.find(myVenueId));
  }
  @Test
  public void addBand_addsBandToVenue() {
    Band myBand = new Band("Household chores");
    myBand.save();
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    myVenue.addBand(myBand);
    Band savedBand = myVenue.getCategories().get(0);
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void getCategories_returnsAllCategories_List() {
    Band myBand = new Band("Household chores");
    myBand.save();
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    myVenue.addBand(myBand);
    List savedCategories = myVenue.getCategories();
    assertEquals(1, savedCategories.size());
  }
  @Test
  public void delete_deletesAllVenuesAndCategoriesAssociations() {
    Band myBand = new Band("Household chores");
    myBand.save();
    Venue myVenue = new Venue("Mow the lawn");
    myVenue.save();
    myVenue.addBand(myBand);
    myVenue.delete();
    assertEquals(0, myBand.getVenues().size());
  }

}
