package imageviewer.architecture;

public class ImagePresenter {

    public static ImagePresenter with(Image image, ImageDisplay imageDisplay) {
        return new ImagePresenter(image, imageDisplay);
    }
    private Image image;
    private final ImageDisplay display;

    public void show(Image image) {
        this.image = image;
        this.refresh();
    }
    
    private ImagePresenter(Image image, ImageDisplay display) {
        this.image = image;
        this.display = display;
        this.display.onDragged(this::onDragged);
        this.display.onReleased(this::onReleased);
        this.refresh();
    }
    
    private void onDragged(int offset) {
        display.clear();
        
        Double ratio = (double) display.width()/image.width();
        
        display.paint(image.data(), offset, ratio);
        if (offset < 0) {
            display.paint(image.next().data(), image.width() + offset, ratio);            
        }
        else {
            Image prev = image.prev();
            display.paint(prev.data(), -prev.width() + offset, ratio);                        
        }
    }
       
    private void onReleased(int offset) {
        if (Math.abs(offset) > display.width() / 2) {
            this.image = offset < 0 ? image.next() : image.prev();
        }
        refresh();
        
    }
    
    private void refresh() {
        
        display.clear();
        Double ratio = (double) display.width()/image.width();
        display.paint(image.data(), 0, ratio);
    }
    
    
    
    
}
