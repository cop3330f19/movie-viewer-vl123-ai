package com.rattlers.movies;

import com.jfoenix.controls.JFXButton;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Movies;

public class FXMLController implements Initializable {
    
    @FXML
    private AnchorPane scene;
    
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w342/";
    final int SPACING = 15;
    private static final Insets PAD_10 = new Insets(10);
    private static final Insets PAD_15 = new Insets(15);
    
    private ArrayList<MovieDb> movies;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadInitialScene();
    } 
    private void loadInitialScene(){
        ScrollPane scpMain = new ScrollPane();
        
        GridPane posterGrid = new GridPane();
        
        Movies movieList = new Movies ();
        
        movies = movieList.getMovies();
        
        int i = 0, col= 0, row= 0;
        
        for (final MovieDb movie :movies)
        {
            Image image = new Image(IMAGE_URL + movie.getPosterPath());
            
            ImageView imView = new ImageView(image);
            final int current = i;
            
            imView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                   loadDetailScene(current);
                }

            });
               
            posterGrid.add(imView,col, row);
            
            i++;
            col++;
            
            if(col%5 == 0){
                row++;
            }
            if(col == 5){
                col = 0;
            }
           
            posterGrid.setHgap(SPACING);
            posterGrid.setVgap(SPACING);
            
            posterGrid.setMaxWidth(800);
            posterGrid.setMaxHeight(1000);
            
            scpMain.setContent(posterGrid);
            scpMain.setPadding(PAD_15);
            scpMain.setFitToWidth(true);
            
            if(scene.getHeight() > 0)
                scpMain.setMaxHeight(scene.getHeight());
            else
                scpMain.setMaxHeight(1000);
            
            scpMain.setMinWidth(1900);
            scene.setMinWidth(1900);
            
            scene.getChildren().add(scpMain);
        }
        
    }
      private void loadDetailScene(int current) {
              
          SplitPane splitPane = new SplitPane();
          StackPane lhs = new StackPane();
          VBox rhs = new VBox();
          
          scene.getChildren().clear();
          
          MovieDb movie = movies.get(current);
          
          ImageView imView = new ImageView( new Image(IMAGE_URL)+ movie.getPosterPath());
          lhs.getChildren().add(imView);
          lhs.setPadding(PAD_10);
          lhs.setMaxWidth(400);
          lhs.setAlignment(Pos.TOP_CENTER);
          
          Label title = new Label();
          title.setText(movie.getTitle());
          title.setStyle("-fx-font:  40 Roboto;");
          rhs.getChildren().add(title);
          
          Movies movieModel = new Movies();
          ArrayList<Video> video = movieModel.getVideos(movie.getId());
          
          if(video.size() > 0){
              String content;
              String videoKey = video.get(0).getKey();
              String site = video.get(0).getSite();
              
              if(site.equals("YouTube")){
                  content = "<iframe width=\"660\" height=\"415\" src=\"https://www.youtube.com/embed/" +videoKey + "frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
              }
              else {
                  content ="<iframe src=\"https://player.vimeo.com/video/"+ videoKey+ "\" width=\"640\" height=\"360\" frameborder=\"0\" allow=\"autoplay; fullscreen\" allowfullscreen></iframe>";
              }
                 
              WebView webView = new WebView();
              webView.setMaxSize(695,450);
              
              WebEngine webEngine = webView.getEngine();
              webEngine.loadContent(content,"text/html");
              webEngine.reload();
              rhs.getChildren().add(webView);
              
              Label details = new Label();
              details.setText(movie.getOverview());
              details.setMaxSize(700,700);
              details.setWrapText(true);
              rhs.getChildren().add(details);
              
              JFXButton btnBack = new JFXButton();
              btnBack.setText("Back");
              btnBack.setPadding(PAD_15);
              btnBack.setOnAction(new EventHandler<ActionEvent>(){

                  @Override
                  public void handle(ActionEvent event) {
                       scene.getChildren().clear();
                  loadInitialScene();
                  }
              });
              
              rhs.getChildren().add(btnBack);
              rhs.setSpacing(SPACING);
              splitPane.setMinHeight(scene.getHeight());
              splitPane.setMinWidth(scene.getWidth());
              
              splitPane.getItems().addAll(lhs,rhs);
              scene.getChildren().add(splitPane);
              
          }
          
                }
}
