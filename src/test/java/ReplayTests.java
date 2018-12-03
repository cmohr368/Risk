 import static org.junit.jupiter.api.Assertions.*;
 import org.junit.jupiter.api.Test;

 public class ReplayTests {


    @Test
     public void getBucketNameTest(){
        Replay replay= new Replay();
        String bucketNameTest = "test";
        replay.bucketName= bucketNameTest;
        assertEquals(replay.getBucketName(), bucketNameTest);
     }

     @Test
     public void getFileKeyTest(){
         Replay replay= new Replay();
         String fileKeyTest = "test";
         replay.fileKey= fileKeyTest;
        assertEquals(replay.getFileKey(), fileKeyTest);
     }

  }
