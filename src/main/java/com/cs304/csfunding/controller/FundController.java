package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.*;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.service.ApplyService;
import com.cs304.csfunding.service.FundService;
import com.cs304.csfunding.service.ResearchGroupService;
import com.cs304.csfunding.service.ResearchGroup_FundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cs304.csfunding.service.Fund_ApplyService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FundController {

    @Autowired
    private FundService fundService;
    @Autowired
    private Fund_ApplyService fund_applyService;
    @Autowired
    private ApplyService applyService;

    @Autowired
    private ResearchGroup_FundService researchGroup_fundService;
    @Autowired
    private ResearchGroupService researchGroupService;

    Logger logger = LoggerFactory.getLogger(FundController.class);


    @PostMapping(value = "/add-fund")
    public Result testAddFund(@RequestBody FundInsertDTO fundDTO) {
        int id = fundService.testInsert(fundDTO);
        logger.info(Integer.toString(id));
        ResearchGroup_FundDTO r_f = new ResearchGroup_FundDTO();
        r_f.setFundUUID(id);
        r_f.setResearchGroupUUID(fundDTO.getResearchGroup());
        researchGroup_fundService.testInsert(r_f);
        researchGroupService.addResearchGroupFund(fundDTO.getResearchGroup(), fundDTO.getSum());
        return new Result("OK");
    }

    @PostMapping(value = "/modify-fund")
    public Result testModifyFund(@RequestBody FundDTO fundDTO) {
        Fund fund = fundService.queryByID(fundDTO.getUuid());
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            return fundService.testModify(fundDTO);
        }
    }

    @GetMapping("delete-fund")
    public Result testDeleteFund(@RequestParam int uuid) {
        Fund fund = fundService.queryByID(uuid);
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            fundService.testDelete(uuid);
            return new Result(200, "success", null);
        }
    }

    @GetMapping("/get-all-funds")
    public Result getAllFund() {
        List<Fund> funds = fundService.testQueryAll();
        if (funds == null) {
            return new Result(404, "funds not found", null);
        } else {
            return new Result(funds);
        }
    }

    @GetMapping("/get-fund")
    public Result getFundByUuid(@RequestParam int uuid) {
        Fund fund = fundService.queryByID(uuid);
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            return new Result(200, "success", fund);
        }
    }

    @GetMapping("/get-fund-vague")
    public Result getFundVague(@RequestParam String FundNumber, @RequestParam String FundName, @RequestParam String researchGroupId) {
        List<Fund> fund = fundService.queryVague(FundNumber, FundName, researchGroupId);
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            return new Result(200, "success", fund);
        }
    }

//    @GetMapping("/get-12month-cost")
//    public Result getCost(@RequestParam int uuid) {
//        Fund fund = fundService.queryByID(uuid);
//        if (fund == null) {
//            return new Result(404, "fund not found", null);
//        } else {
//            List<Apply> applies = applyService.testQueryByFundID(uuid);
//            Map<Integer, Integer> map = new HashMap<>();
//            for (int i = 0; i < 12; i++) {
//                map.put(i + 1, 0);
//            }
//            long timeMill = System.currentTimeMillis();
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(timeMill);
//            int currentMonth = calendar.get(Calendar.MONTH) + 1;
//            int currentYear = calendar.get(Calendar.YEAR);
//            int applyMonth;
//
//            for (Apply apply : applies) {
//                if (!apply.getState().equals("pass"))
//                    continue;
//                String time = apply.getTime();
//                // Convert the time string to a Date object
//                calendar.setTimeInMillis(Long.parseLong(time));
//                if (calendar.get(Calendar.YEAR) == currentYear - 1 && calendar.get(Calendar.MONTH) + 1 > currentMonth) {
//                    applyMonth = calendar.get(Calendar.MONTH) + 1;
//                } else if (calendar.get(Calendar.YEAR) == currentYear) {
//                    applyMonth = calendar.get(Calendar.MONTH) + 1;
//                } else {
//                    continue;
//                }
//                map.put(applyMonth, map.get(applyMonth) + apply.getMoney());
//            }
//            List<Map<String, Integer>> res = new ArrayList<>();
//            for (int i = 0; i < 12; i++) {
//                Map<String, Integer> m = new HashMap<>();
//                m.put("name", (i + currentMonth) % 12 + 1);
//                m.put("value", map.get((i + currentMonth) % 12 + 1));
//                res.add(m);
//            }
//            return new Result(200, "success", res);
//        }
//    }

//    @GetMapping("/get-excel")
//    public Result getExcel(@RequestParam String path) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//
//        Sheet sheet = workbook.createSheet("多项经费使用一览表");
//
//        Row dataRow = sheet.createRow(0);
//        Cell dataCell = dataRow.createCell(0);
//        dataCell.setCellValue("多项经费使用一览表");
//
//        dataRow = sheet.createRow(1);
//        dataCell = dataRow.createCell(0);
//        dataCell.setCellValue("序号");
//
//        dataCell = dataRow.createCell(2);
//        dataCell.setCellValue("经费名称（编号）");
//        dataCell = dataRow.createCell(3);
//        dataCell.setCellValue("经费授权有效期");
//        dataCell = dataRow.createCell(4);
//        dataCell.setCellValue("经费总额");
//        dataCell = dataRow.createCell(5);
//        dataCell.setCellValue("已使用经费");
//        dataCell = dataRow.createCell(6);
//        dataCell.setCellValue("经费余额");
//        dataCell = dataRow.createCell(7);
//        dataCell.setCellValue("当前是否达标");
//
//        dataRow = sheet.createRow(2);
//        dataCell = dataRow.createCell(0);
//        dataCell.setCellValue(1);
//        dataCell = dataRow.createCell(2);
//        dataCell.setCellValue("高水平1（G000XX）");
//        dataCell = dataRow.createCell(3);
//        dataCell.setCellValue("2022.05.08-2023.06.28");
//        dataCell = dataRow.createCell(4);
//        dataCell.setCellValue("500");
//        dataCell = dataRow.createCell(5);
//        dataCell.setCellValue("300");
//        dataCell = dataRow.createCell(6);
//        dataCell.setCellValue("200");
//        dataCell = dataRow.createCell(7);
//        dataCell.setCellValue("是");
//
//        dataRow = sheet.createRow(3);
//        dataCell = dataRow.createCell(0);
//        dataCell.setCellValue(2);
//        dataCell = dataRow.createCell(2);
//        dataCell.setCellValue("高水平2（G001XX）");
//        dataCell = dataRow.createCell(3);
//        dataCell.setCellValue("2023.05.29-2023.06.28");
//        dataCell = dataRow.createCell(4);
//        dataCell.setCellValue("1000");
//        dataCell = dataRow.createCell(5);
//        dataCell.setCellValue("700");
//        dataCell = dataRow.createCell(6);
//        dataCell.setCellValue("300");
//        dataCell = dataRow.createCell(7);
//        dataCell.setCellValue("是");
//
//        dataRow = sheet.createRow(4);
//        dataCell = dataRow.createCell(0);
//        dataCell.setCellValue(3);
//        dataCell = dataRow.createCell(2);
//        dataCell.setCellValue("高水平3（G002XX）");
//        dataCell = dataRow.createCell(3);
//        dataCell.setCellValue("2023.05.15-2023.06.21");
//        dataCell = dataRow.createCell(4);
//        dataCell.setCellValue("1500");
//        dataCell = dataRow.createCell(5);
//        dataCell.setCellValue("700");
//        dataCell = dataRow.createCell(6);
//        dataCell.setCellValue("800");
//        dataCell = dataRow.createCell(7);
//        dataCell.setCellValue("否");
//
//        dataRow = sheet.createRow(5);
//        dataCell = dataRow.createCell(0);
//        dataCell.setCellValue(3);
//        dataCell = dataRow.createCell(2);
//        dataCell.setCellValue("高水平4（G003XX）");
//        dataCell = dataRow.createCell(3);
//        dataCell.setCellValue("2023.05.15-2023.6.21");
//        dataCell = dataRow.createCell(4);
//        dataCell.setCellValue("2000");
//        dataCell = dataRow.createCell(5);
//        dataCell.setCellValue("500");
//        dataCell = dataRow.createCell(6);
//        dataCell.setCellValue("1500");
//        dataCell = dataRow.createCell(7);
//        dataCell.setCellValue("否");
//
//
//        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Xieyudong\\Desktop\\"+path+"\\data.xlsx");
//        workbook.write(fileOut);
//        fileOut.close();
//        return new Result(200);
//    }
}
