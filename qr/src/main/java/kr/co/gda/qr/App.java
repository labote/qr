package kr.co.gda.qr;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class App extends JFrame {
	static Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws WriterException, IOException {

        
    	// 1. QR에 어떤 컨텐츠(문자열 영문자 4000자 정도)를 추가할 것인가? -> 다른 API를 통해서 획득 // QRService
        QRService qrService = new QRService();
        String userName = qrService.getUserName();
        String userAge = qrService.getUserAge();
        
        userName = new String(userName.getBytes("UTF-8"), "ISO-8859-1"); // 한글깨지는 현상을 막기 위해 UTF-8 사용
        
        // StringBuffer 객체를 만들고 그 안에 이름과 나이를 넣는다
        StringBuffer contents = new StringBuffer();
        contents.append("name : " + userName);
        log.info(userName);
        contents.append(", age : "+userAge);
        log.info(userAge);
        
        // 2. QR 생성 -> QR에 컨텐츠 추가
        QRCodeWriter qrWriter = new QRCodeWriter();
        // QR 이미지 관련(모양, 데이터 : 컨텐츠 추가)
        BitMatrix martix = qrWriter.encode(contents.toString(), BarcodeFormat.QR_CODE, 300, 300);
        // QR 설정(config, 색상)
        // MatrixToImageConfig config = new MatrixToImageConfig(0xFFFFFFFF, 0xFF000000); 
       
        // 두 개의 설정 매개변수를 이용하여 이미지 생성
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(martix);
       
        // 3. QR 저장
        String imageFileName = "myqr.png";
        ImageIO.write(qrImage, "png", new File(imageFileName));  // 메모리 안의 이미지, 확장자, 파일 생성
        
        // 4. QR 출력 -> web이면 jsp view, pc app이면 swing frame -> android app이면 activity
        App app = new App();
        app.setTitle("QR"); // app title
        app.setLayout(new FlowLayout()); // layout
        
        // 이미지 아이콘 생성 및 label 생성
        ImageIcon icon = new ImageIcon(imageFileName);
        JLabel imageLabel = new JLabel(icon);
        app.add(imageLabel);
        
        app.setSize(400, 400);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X 클릭하면 종료
    }
}
