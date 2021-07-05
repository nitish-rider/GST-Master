package gst.data.master;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.http.server.types.files.SystemFile;
import io.reactivex.Single;
import org.reactivestreams.Publisher;

import java.io.*;

@Controller("/uploadFile")
public class ApiController {
    public final MediaType xls = new MediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "GST";
    }


//    @Post(value = "/", consumes = [MediaType.MULTIPART_FORM_DATA], produces = [MediaType.TEXT_PLAIN])
//    fun upload(file: StreamingFileUpload): Single<HttpResponse<String>> {
//        val tempFile = File.createTempFile(file.filename, "temp")
//        val uploadPublisher = file.transferTo(tempFile)
//        return Single.fromPublisher(uploadPublisher) .map { success ->
//            if (success) {
//                HttpResponse.ok("Uploaded")
//            }
//            else {
//                HttpResponse.status<String>(HttpStatus.CONFLICT) .body("Upload Failed")
//            }
//        }
//    }

    @Post(consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.MULTIPART_FORM_DATA)
    public Single<HttpResponse> upload( StreamingFileUpload file) throws IOException {
        String tag = System.currentTimeMillis() + "";

        String pathname = "sample" + tag + ".xlsx";
        File tempFile  = new File(pathname);
        Publisher<Boolean> uploadPublisher = file.transferTo(tempFile);

        return Single.fromPublisher(uploadPublisher).map( success -> {
            if (success) {
                Main obj = new Main();
                String out = "output" + tag + ".xls";
                File finalFile= obj.calculateFile(tempFile);

                return HttpResponse.ok(finalFile);
            }
            else {
                return HttpResponse.status(HttpStatus.CONFLICT).body("Upload Failed");
            }
        } );
    }

}
