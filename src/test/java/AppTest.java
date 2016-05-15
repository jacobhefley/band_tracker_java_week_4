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
    assertThat(pageSource()).contains("Band Tracker");
  }

  @Test
  public void bandIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands"));
    fill("#name").with("House");
    submit(".btn");
    assertThat(pageSource()).contains("House");
  }

  @Test
  public void venueIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Venues"));
    fill("#description").with("Tavern");
    submit(".btn");
    assertThat(pageSource()).contains("Tavern");
  }

  @Test
  public void bandShowPageDisplaysName() {
    Band testBand = new Band("House");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("House");
  }

  @Test
  public void venueIsAddedToBand() {
    Band testBand = new Band("House");
    testBand.save();
    Venue testVenue = new Venue("Downtown");
    testVenue.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    fillSelect("#venue_id").withText("Downtown");
    submit(".btn btn-success");
    assertThat(pageSource()).contains("Downtown");
  }

  @Test
  public void bandNameIsUpdated() {
    Band testBand = new Band("House");
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
    Band testBand = new Band("House");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    submit("#delete");
    goTo(url);
    assertThat(pageSource()).contains("$band.getName()");
  }

}
