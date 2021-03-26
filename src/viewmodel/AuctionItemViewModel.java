package viewmodel;

import javafx.beans.property.*;
import javafx.application.Platform;
import model.AuctionModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AuctionItemViewModel implements PropertyChangeListener
{
  private StringProperty item;
  private StringProperty time;
  private IntegerProperty bid;
  private StringProperty bidder;
  private IntegerProperty currentBid;
  private StringProperty currentBidder;
  private StringProperty error;
  private BooleanProperty end;
  private StringProperty currentBidTitle;
  private AuctionModel model;

  public AuctionItemViewModel(AuctionModel model){
    this.model = model;
    item = new SimpleStringProperty(model.getItem());
    time = new SimpleStringProperty();
    bid = new SimpleIntegerProperty();
    bidder = new SimpleStringProperty("You");
    currentBid = new SimpleIntegerProperty(model.getCurrentBid());
    currentBidder = new SimpleStringProperty(model.getCurrentBidder());
    error = new SimpleStringProperty();
    end = new SimpleBooleanProperty();
    currentBidTitle = new SimpleStringProperty("Bid: ");

    model.addListener("bid", this);
    model.addListener("time", this);
    model.addListener("end", this);
  }

  public void clear(){
    item.set(null);
    time.set(null);
    bid.set(0);
    bidder.set(null);
    currentBid.set(0);
    currentBidder.set(null);
    error.set(null);
    end.set(false);
    currentBidTitle.set(null);
  }

  public void bid(){
      if (!model.placeBid(bid.get(), bidder.get()))
      {
        Platform.runLater(() -> error.set("Illegal bid"));
      }
      else
      {
        Platform.runLater(() -> error.set(null));
      }
  }

  public StringProperty getItemProperty()
  {
    return item;
  }

  public StringProperty getTimeProperty()
  {
    return time;
  }

  public IntegerProperty getBidProperty()
  {
    return bid;
  }

  public StringProperty getBidderProperty()
  {
    return bidder;
  }

  public IntegerProperty getCurrentBidProperty()
  {
    return currentBid;
  }

  public StringProperty getCurrentBidderProperty()
  {
    return currentBidder;
  }

  public StringProperty getErrorProperty()
  {
    return error;
  }

  public BooleanProperty getEndProperty()
  {
    return end;
  }

  public StringProperty getCurrentBidTitleProperty()
  {
    return currentBidTitle;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName()) {
      case "bid":
        Platform.runLater(() -> {
          currentBid.set(model.getCurrentBid());
          currentBidder.set(model.getCurrentBidder());
        });
        break;
      case "time":
        Platform.runLater(() -> time.set(
                String.format("00:00:%02d", model.getRemainingTimeInSeconds()))
        );
        break;
      case "end":
        Platform.runLater(() -> {
          currentBidTitle.set("Final Bid: ");
          error.set("Bid finished");
        });
        end.set(true);
        break;
    }
  }
}
