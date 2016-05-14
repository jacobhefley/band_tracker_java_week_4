import java.util.*;
import org.sql2o.*;

public class Venue {
  private int id;
  private String description;

  public Venue(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public static List<Venue> all() {
    String sql = "SELECT id, description FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getDescription().equals(newVenue.getDescription()) &&
             this.getId() == newVenue.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues(description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where id=:id";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }
  public void update(String newDescription) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE venues SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", newDescription)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addBand(Band band) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
        .addParameter("band_id", band.getId())
        .addParameter("venue_id", this.getId())
        .executeUpdate();
    }
  }


  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT band_id FROM bands_venues WHERE venue_id = :venue_id";
      List<Integer> bandIds = con.createQuery(joinQuery)
        .addParameter("venue_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Band> bands = new ArrayList<Band>();

      for (Integer bandId : bandIds) {
        String venueQuery = "SELECT * FROM bands WHERE id = :bandId";
        Band band = con.createQuery(venueQuery)
          .addParameter("bandId", bandId)
          .executeAndFetchFirst(Band.class);
        bands.add(band);
      }
      return bands;
    }
  }
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM venues WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", this.id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM bands_venues WHERE venue_id = :venueId";
        con.createQuery(joinDeleteQuery)
          .addParameter("venueId", this.id)
          .executeUpdate();
    }
  }
}
