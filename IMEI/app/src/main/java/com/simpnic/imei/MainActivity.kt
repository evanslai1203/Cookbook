package com.simpnic.imei

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val telephonyManager = this.getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        /**
         * 請求來自設備上所有無線電的所有可用cell信息，包括預佔/註冊，服務和相鄰cell。
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                telephonyManager.allCellInfo
            }
        }

        /** 電話狀態：
       * 1.CALL_STATE_IDLE=0     無活動
       * 2.CALL_STATE_RINGING=1  響鈴
       * 3.CALL_STATE_OFFHOOK=2  摘機
       */
        telephonyManager.callState//int


        /**
         * 數據連接上的活動類型(cellular)
         * 1.DATA_ACTIVITY_NONE
         * 2.DATA_ACTIVITY_IN
         * 3.DATA_ACTIVITY_OUT
         * 4.DATA_ACTIVITY_INOUT
         * 5.DATA_ACTIVITY_DORMANT
         */
        telephonyManager.dataActivity//int


        /**
         * 當前數據連接狀態（cellular）
         * DATA_DISCONNECTED
         * DATA_CONNECTING
         * DATA_CONNECTED
         * DATA_SUSPENDED
         */
        telephonyManager.dataState//int


        /**
         * Returns the software version number for the device, for example, the IMEI/SV for GSM phones.
         * turn null if the software version is not available.
         */
        telephonyManager.deviceSoftwareVersion//String


        /**
         * Get the emergency number list based on current locale, sim, default, modem and network.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            telephonyManager.emergencyNumberList//Map
        }


        /**
         * Returns the IMEI (International Mobile Equipment Identity). Return null if IMEI is not available.
         * Requires android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.imei//String
        }


        /**
         * Returns the MEID (Mobile Equipment Identifier). Return null if MEID is not available.
         * Requires android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.meid//String
        }

        /**
         * Returns the Network Access Identifier (NAI). Return null if NAI is not available.
         * Requires Manifest.permission.READ_PHONE_STATE
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            telephonyManager.nai
        }

        /** 獲取ISO標準的國家碼，即國際長途區號。
         * 注意：僅當使用者已在網路註冊後有效。
         * 在CDMA網路中結果也許不可靠。
         */
        telephonyManager.networkCountryIso//String

        /** MCC MNC(mobile country code mobile network code)
         * 注意：僅當使用者已在網路註冊時有效。
         * 在CDMA網路中結果也許不可靠。
         */
        telephonyManager.networkOperator//String

        /** 按照字母次序的current registered operator(當前已註冊的使用者)的名字
         * 注意：僅當使用者已在網路註冊時有效。
         * 在CDMA網路中結果也許不可靠。
         */
        telephonyManager.networkOperatorName//String

        /** 當前使用的網路型別：
         * 例如： NETWORK_TYPE_UNKNOWN  網路型別未知  0
         * NETWORK_TYPE_GPRS     GPRS網路  1
         * NETWORK_TYPE_EDGE     EDGE網路  2
         * NETWORK_TYPE_UMTS     UMTS網路  3
         * NETWORK_TYPE_HSDPA    HSDPA網路  8
         * NETWORK_TYPE_HSUPA    HSUPA網路  9
         * NETWORK_TYPE_HSPA     HSPA網路  10
         * NETWORK_TYPE_CDMA     CDMA網路,IS95A 或 IS95B.  4
         * NETWORK_TYPE_EVDO_0   EVDO網路, revision 0.  5
         * NETWORK_TYPE_EVDO_A   EVDO網路, revision A.  6
         * NETWORK_TYPE_1xRTT    1xRTT網路  7
         */
        telephonyManager.networkType//int


        /**
         * 手機型別：
         * 例如： PHONE_TYPE_NONE  無訊號
         * PHONE_TYPE_GSM   GSM訊號
         * PHONE_TYPE_CDMA  CDMA訊號
         * */
        telephonyManager.phoneType//int

        /**
       * Returns the ISO country code equivalent for the SIM provider's country code.
       * 獲取ISO國家碼，相當於提供SIM卡的國家碼。
       *
       */
        telephonyManager.simCountryIso//String

    /** Returns the MCC MNC (mobile country code mobile network code) of the provider of the SIM. 5 or 6 decimal digits.
       * 獲取SIM卡提供的移動國家碼和行動網路碼.5或6位的十進位制數字.
       * SIM卡的狀態必須是 SIM_STATE_READY(使用getSimState()判斷).
       */
        telephonyManager.simOperator//String

    /** 服務商名稱：
       * 例如：中國移動、聯通
       * SIM卡的狀態必須是 SIM_STATE_READY(使用getSimState()判斷).
       */
        telephonyManager.simOperatorName//String


/** SIM的狀態資訊：
   *  SIM_STATE_UNKNOWN          未知狀態 0
   SIM_STATE_ABSENT           沒插卡 1
   SIM_STATE_PIN_REQUIRED     鎖定狀態，需要使用者的PIN碼解鎖 2
   SIM_STATE_PUK_REQUIRED     鎖定狀態，需要使用者的PUK碼解鎖 3
   SIM_STATE_NETWORK_LOCKED   鎖定狀態，需要網路的PIN碼解鎖 4
   SIM_STATE_READY            就緒狀態 5
   */
        telephonyManager.simState//int

/**
 * 取得和語音郵件相關的標籤，即為識別符
   * 需要許可權：READ_PHONE_STATE
   */
        telephonyManager.voiceMailAlphaTag//String

/**
 * 獲取語音郵件號碼：
   * 需要許可權：READ_PHONE_STATE
   */
        telephonyManager.voiceMailNumber//String

/**
 * ICC卡是否存在
   */
        telephonyManager.hasIccCard()//boolean

    }
}
