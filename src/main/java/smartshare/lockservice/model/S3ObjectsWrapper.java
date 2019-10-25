package smartshare.lockservice.model;

import java.util.Iterator;
import java.util.List;

public class S3ObjectsWrapper implements Iterable {

    private List<S3Object> objects;

    @Override
    public Iterator<S3Object> iterator() {
        return this.objects.iterator();
    }
}
