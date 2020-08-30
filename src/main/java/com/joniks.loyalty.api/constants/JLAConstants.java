package com.joniks.lotalty.api.constants;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JLAConstants {
	public static final String KEY_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	public static final String VALUE_EXPOSE_HEADERS = "Content-Type,Content-Disposition,content-length,app-alert-message,app-alert-type,X-Total-Count,X-Prompt-Comment,SESSION_TOKEN,app-alert-time,app-template,app-object,app-file-type";
	public static final String APP_ALERT_MESSAGE = "app-alert-message";
	public static final String APP_ALERT_TYPE = "app-alert-type";
	public static final String APP_ALERT_TIME = "app-alert-time";
	public static final String APP_ALERT_TYPE_WARNING = "warning";
	public static final String APP_ALERT_TYPE_SUCCESS = "success";
	public static final String APP_ALERT_TYPE_DANGER = "danger";
	public static final String APP_ALERT_TYPE_INFO = "info";
	public static final int SCAN_DEMOGRAPHIC = 1;
	public static final int SCAN_SPECIMEN = 2;
	public static final String DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy";
	public static final String DATE_FORMAT_MMDDYYYYHHMM = "MM/dd/yyyy hh:mm";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";
	public static final String DATE_FORMAT_DDMMYYYY = "dd/MM/yyyy";
	public static final String DATE_FORMAT_DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";
	public static final String DATE_FORMAT_DASH_DDMMYYYYHHMM = "dd-MM-yyyy HH-mm";
	public static final String DATE_FORMAT_MMdd = "MMdd";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";

	public static final String HEADER_TOTAL_COUNT = "X-Total-Count";
	public static final String HEADER_PROMPT_COMMENT = "X-Prompt-Comment";
	public static final long TEST_GROUP_INITIAL_SERIAL_VALUE = 100000000;
	public static final List<String> TEST_GROUP_VALID_EXTRACTION_POSITIONS = Arrays.asList(new String[] { "A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4", "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6", "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "A9",
			"B9", "C9", "D9", "E9", "F9", "G9", "H9", "A10", "B10", "C10", "D10", "E10", "F10", "G10", "H10", "A11", "B11", "C11", "D11", "E11", "F11", "G11", "H11", "A12", "B12", "C12", "D12", "E12", "F12", "G12", "H12" });

	public static final List<String> TEST_GROUP_VALID_PCR_POSITIONS = Arrays.asList(new String[] { "A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4", "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6", "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "A9", "B9", "C9",
			"D9", "E9", "F9", "G9", "H9", "A10", "B10", "C10", "D10", "E10", "F10", "G10", "H10", "A11", "B11", "C11", "D11", "E11", "F11", "G11", "H11", "C12", "D12", "E12", "F12", });

	public static final List<String> TEST_RESULT_STATUS = Arrays.asList(new String[] { "Currently Testing", "SARS-CoV-2 viral RNA not detected", "SARS-CoV-2 viral RNA detected", "Equivocal", "Pending for Re-run", "Pending for Re-extraction", "Pending for Physician Validation" });
	public static final List<String> TEST_RESULT_STATUS_FOR_REPORT = Arrays.asList(new String[] { "Currently Testing", "Negative", "Positive", "Equivocal", "Pending for Re-run", "Pending for Re-extraction", "Pending for Physician Validation" });

	public static final int TEST_GROUP_EXTRACTION = 0;
	public static final int TEST_GROUP_PCR = 1;

	public static final String NEGATIVE_CERTIFICATE_TEMPLATE_LOCATION = Paths.get("").toAbsolutePath().toString() + File.separator + "templates" + File.separator + "NEGATIVE.pdf";
	public static final String POSITIVE_CERTIFICATE_TEMPLATE_LOCATION = Paths.get("").toAbsolutePath().toString() + File.separator + "templates" + File.separator + "POSITIVE.pdf";
	public static final String PHILHEALTH_CSF_TEMPLATE_LOCATION = Paths.get("").toAbsolutePath().toString() + File.separator + "templates" + File.separator + "CSF.pdf";
	public static final String PHILHEALTH_CIF_TEMPLATE_LOCATION = Paths.get("").toAbsolutePath().toString() + File.separator + "templates" + File.separator + "CIF.pdf";
	public static final String PHILHEALTH_BS_TEMPLATE_LOCATION = Paths.get("").toAbsolutePath().toString() + File.separator + "templates" + File.separator + "BS.pdf";

	public static final String[] SPECIMEN_TYPES = new String[] { "OPS & NPS", "NPS", "OPS", "SERUM", "ETA", "TA", "SPUTUM", "STOOL", "BLOOD", "ENVIRONMENTAL SAMPLE", "NGT", "ETA SWAB IN VTM/UTM", "OTHER" };
	public static final String TIMEZONE_ASIA_MANILA = "GMT+8:00";
	public static final String TIMEZONE_UTC = "UTC+0:00";
	public static final Integer PASSENGER_TYPE_OUTPATIENT = 1;
	public static final Integer PASSENGER_TYPE_OWWA = 2;
	public static final Integer PASSENGER_TYPE_OTHERS = 3;
}
