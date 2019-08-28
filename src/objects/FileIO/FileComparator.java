package objects.FileIO;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        int a = Integer.valueOf(o1.getName());
        int b = Integer.valueOf(o2.getName());
        if(a > b)return 1;
        else if(a == b)return 0;
        else{
            return -1;
        }
    }
}
