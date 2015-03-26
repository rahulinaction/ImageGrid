package imagegrid.rendevezous.org.imagegrid.model;

public class ImageElement{

    public Integer id;
    public String url;
    public String title;
    public Integer width;
    public Integer height;
    public Integer getId() {
        return this.id;
    }

    public String getUrl(){
        return this.url;
    }

    public String getTitle(){
        return this.title;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setId(Integer _id){
        this.id = _id;
    }

    public void setUrl(String _url){
        this.url = _url;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setHeight(Integer _height) {
        this.height = _height;
    }

    public void setWidth(Integer _width) {
        this.width = _width;
    }
}