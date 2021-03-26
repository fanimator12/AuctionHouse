package view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import viewmodel.AuctionItemViewModel;

public class AuctionItemViewController
{
  @FXML private Label itemLabel;
  @FXML private Label timeLabel;
  @FXML private Label currentBidTitle;
  @FXML private Label currentBidLabel;
  @FXML private Label currentBidderLabel;
  @FXML private TextField bidField;
  @FXML private Label errorLabel;

  private Button bidButton;
  private ViewHandler viewHandler;
  private AuctionItemViewModel viewModel;
  private Region root;

  public AuctionItemViewController()
  {
  }

  public void init(ViewHandler viewHandler, AuctionItemViewModel viewModel, Region root)
  {
    this.root = root;
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;

    itemLabel.textProperty().bind(viewModel.getItemProperty());
    timeLabel.textProperty().bind(viewModel.getTimeProperty());
    currentBidTitle.textProperty().bind(viewModel.getCurrentBidTitleProperty());
    currentBidderLabel.textProperty().bind(viewModel.getCurrentBidderProperty());
    errorLabel.textProperty().bind(viewModel.getErrorProperty());

    Bindings.bindBidirectional(bidField.textProperty(),
            viewModel.getBidProperty(), new StringIntegerConverter(0));
    Bindings.bindBidirectional(currentBidLabel.textProperty(),
            viewModel.getCurrentBidProperty(), new StringIntegerConverter(0));

    viewModel.getEndProperty().addListener((obs, oldValue, newValue) -> {
      timeLabel.setStyle("-fx-background-color:RED");
      bidButton.setDisable(true);
    });
  }

  public void reset()
  {
    itemLabel.setText(null);
    timeLabel.setText(null);
    currentBidTitle.setText(null);
    currentBidderLabel.setText(null);
    currentBidLabel.setText(null);
    bidField.setText(null);
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML private void bidOnAction()
  {
    viewModel.bid();
  }
}

