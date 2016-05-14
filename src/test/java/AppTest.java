import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Todo list!");
  }

  @Test
  public void bandIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands"));
    fill("#name").with("Household chores");
    submit(".btn");
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void venueIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Venues"));
    fill("#description").with("Mow the lawn");
    submit(".btn");
    assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void bandShowPageDisplaysName() {
    Band testBand = new Band("Household chores");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void venueShowPageDisplaysDescription() {
    Venue testVenue = new Venue("Mow the lawn");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void venueIsAddedToBand() {
    Band testBand = new Band("Household chores");
    testBand.save();
    Venue testVenue = new Venue("Mow the lawn");
    testVenue.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    fillSelect("#venue_id").withText("Mow the lawn");
    submit(".btn");
    assertThat(pageSource()).contains("<li>");
    assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void bandIsAddedToVenue() {
    Band testBand = new Band("Household chores");
    testBand.save();
    Venue testVenue = new Venue("Mow the lawn");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    fillSelect("#band_id").withText("Household chores");
    submit(".btn");
    assertThat(pageSource()).contains("<li>");
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void bandNameIsUpdated() {
    Band testBand = new Band("Household chores");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    click("a", withText("Edit this band"));
    fill("#name").with("Fun things");
    submit(".btn");
    goTo(url);
    assertThat(pageSource()).contains("Fun things");
  }

  @Test
  public void bandIsDeleted() {
    Band testBand = new Band("Household chores");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    submit("#delete");
    goTo(url);
    assertThat(pageSource()).contains("$band.getName()");
  }
  @Test
  public void venueDescriptionIsUpdated() {
    Venue testVenue = new Venue("Mow the lawn");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    click("a", withText("Edit this venue"));
    fill("#description").with("Go out dancing");
    submit(".btn");
    goTo(url);
    assertThat(pageSource()).contains("Go out dancing");
  }

  @Test
  public void venueIsDeleted() {
    Venue testVenue = new Venue("Mow the lawn");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    submit("#delete");
    goTo(url);
    assertThat(pageSource()).contains("$venue.getDescription()");
  }

}
