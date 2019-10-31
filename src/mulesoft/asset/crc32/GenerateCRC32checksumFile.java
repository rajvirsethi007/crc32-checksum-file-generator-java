package mulesoft.asset.crc32;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import jonelo.jacksum.algorithm.Cksum;

public class GenerateCRC32checksumFile {
	
		public void getChecksumFile(String orgFilename, String inputPath, String outputPath) {
			
			String pathname = inputPath+"/"+orgFilename;
			
			String auditFilename = orgFilename+".aud";
			String auditPathfile = outputPath+"/"+auditFilename;
			
			InputStream inputStream;
			BufferedWriter output;
			String crc32Value;
			
				inputStream = null;
				output = null;
				
				String fileSize = getFileSizeInBytes(pathname); 
				
				try {
					
					 crc32Value = getCRC32valueFromInputFile(pathname, inputStream);
					 
				 } catch (IOException e) {	
					 
					 crc32Value = "wrong value has been produced";
					 System.out.println("CRC32 exception thrown: " +e);
					 e.printStackTrace();
				}
			
				try {
				
					createCRC32file(output, crc32Value, fileSize, orgFilename, auditPathfile);
				
				} catch (IOException e) {
							System.out.println("check sum file exception thrown: " +e);
							e.printStackTrace();
						} 
				
			}

		private void createCRC32file(BufferedWriter output, String crc32Value, String fileSize, String orgFilename, String auditPathfile) throws IOException {
			
			String auditFileContent = crc32Value +" "+ fileSize + " " + orgFilename; 
			File auditFile = new File(auditPathfile);
		
			System.out.println("cksum 6 ==== Audit file initiated");
			
			output = new BufferedWriter(new FileWriter(auditFile));
			output.write(auditFileContent);	
			
			System.out.println("cksum 7 ==== Cksum file generated");
			output.close();
		}

		private String getCRC32valueFromInputFile(String pathname, InputStream inputStream) throws IOException {
			
			System.out.println("cksum 1 ==== Final file initiated");
			
			inputStream = new FileInputStream(pathname);
			System.out.println("cksum 2 ==== final file converted to inputstream");

			Cksum crc = new Cksum();
			int cnt;
			System.out.println("cksum 3 ==== CRC32 initiated");
			
			while ((cnt = inputStream.read()) != -1) {
					crc.update(cnt);
				   }
			inputStream.close();
			
			System.out.println("cksum 4 ==== CRC32 completed and Inputstream closed");
			
			return String.valueOf(crc.getValue());
		 
		}

		private String getFileSizeInBytes(String pathname) {
			File mainFile = new File(pathname);
			return String.valueOf(mainFile.length());
		}
	
}
