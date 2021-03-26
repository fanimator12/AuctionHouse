package model;

import utility.NamedPropertyChangeSubject;

public interface AuctionModel extends NamedPropertyChangeSubject
{
  public boolean placeBid(int bid, String bidder);
  public String getItem();
  public int getCurrentBid();
  public String getCurrentBidder();
  public int getRemainingTimeInSeconds();
}
