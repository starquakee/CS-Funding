package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.api.FundInsertDTO;
import com.cs304.csfunding.api.ResearchGroup_FundDTO;
import com.cs304.csfunding.api.Result;
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

    @GetMapping("/get-12month-cost")
    public Result getCost(@RequestParam int uuid) {
        Fund fund = fundService.queryByID(uuid);
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            List<Apply> applies = applyService.testQueryByFundID(uuid);
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < 12; i++) {
                map.put(i+1, 0);
            }
            long timeMill = System.currentTimeMillis();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeMill);
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int currentYear = calendar.get(Calendar.YEAR);
            int applyMonth;

            for (Apply apply : applies) {
                String time = apply.getTime();
                // Convert the time string to a Date object
                calendar.setTimeInMillis(Long.parseLong(time));
                if (calendar.get(Calendar.YEAR)==currentYear-1 && calendar.get(Calendar.MONTH)+1>currentMonth) {
                    applyMonth = calendar.get(Calendar.MONTH) + 1;
                } else if (calendar.get(Calendar.YEAR)==currentYear) {
                    applyMonth = calendar.get(Calendar.MONTH) + 1;
                } else {
                    continue;
                }
                map.put(applyMonth, map.get(applyMonth)+apply.getMoney());
            }
            List<Map<String, Integer>> res = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                Map<String, Integer> m = new HashMap<>();
                m.put("name", i+1);
                m.put("value", map.get(i+1));
                res.add(m);
            }
            return new Result(200, "success", res);
        }
    }

    @GetMapping("/get-excel")
    public Result getExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("多项经费使用一览表");

        Row dataRow = sheet.createRow(0);
        Cell dataCell = dataRow.createCell(0);
        dataCell.setCellValue("多项经费使用一览表");

        dataRow = sheet.createRow(1);
        dataCell = dataRow.createCell(0);
        dataCell.setCellValue("序号");
        dataCell = dataRow.createCell(1);
        dataCell.setCellValue("经费编号");
        dataCell = dataRow.createCell(2);
        dataCell.setCellValue("经费名称");
        dataCell = dataRow.createCell(3);
        dataCell.setCellValue("经费授权有效期");
        dataCell = dataRow.createCell(4);
        dataCell.setCellValue("经费总额");
        dataCell = dataRow.createCell(5);
        dataCell.setCellValue("已使用经费");
        dataCell = dataRow.createCell(6);
        dataCell.setCellValue("经费余额");
        dataCell = dataRow.createCell(7);
        dataCell.setCellValue("剩余时间天数");
        dataCell = dataRow.createCell(8);
        dataCell.setCellValue("当前执行率");
        dataCell = dataRow.createCell(9);
        dataCell.setCellValue("当前是否达标");

        dataRow = sheet.createRow(2);
        dataCell = dataRow.createCell(0);
        dataCell.setCellValue(1);
        dataCell = dataRow.createCell(2);
        dataCell.setCellValue("国自然（Y012300XX）");
        dataCell = dataRow.createCell(3);
        dataCell.setCellValue("2019.01.01-2020.01.01");
        dataCell = dataRow.createCell(4);
        dataCell.setCellValue("300.0");
        dataCell = dataRow.createCell(5);
        dataCell.setCellValue("100.0");
        dataCell = dataRow.createCell(6);
        dataCell.setCellValue("200.0");
        dataCell = dataRow.createCell(7);
        dataCell.setCellValue("0");
        dataCell = dataRow.createCell(8);
        dataCell.setCellValue("33%");
        dataCell = dataRow.createCell(9);
        dataCell.setCellValue("否");

        dataRow = sheet.createRow(3);
        dataCell = dataRow.createCell(0);
        dataCell.setCellValue(2);
        dataCell = dataRow.createCell(2);
        dataCell.setCellValue("中央财政支持地方高校经费（Y120XX）");
        dataCell = dataRow.createCell(3);
        dataCell.setCellValue("2020.03.01-2021.02.02");
        dataCell = dataRow.createCell(4);
        dataCell.setCellValue("100.0");
        dataCell = dataRow.createCell(5);
        dataCell.setCellValue("80.0");
        dataCell = dataRow.createCell(6);
        dataCell.setCellValue("20.0");
        dataCell = dataRow.createCell(7);
        dataCell.setCellValue("340");
        dataCell = dataRow.createCell(8);
        dataCell.setCellValue("80%");
        dataCell = dataRow.createCell(9);
        dataCell.setCellValue("是");

        dataRow = sheet.createRow(4);
        dataCell = dataRow.createCell(0);
        dataCell.setCellValue(3);
        dataCell = dataRow.createCell(2);
        dataCell.setCellValue("高水平（G000XX）");
        dataCell = dataRow.createCell(3);
        dataCell.setCellValue("2020.05.01-2020.10.03");
        dataCell = dataRow.createCell(4);
        dataCell.setCellValue("500.0");
        dataCell = dataRow.createCell(5);
        dataCell.setCellValue("200.0");
        dataCell = dataRow.createCell(6);
        dataCell.setCellValue("300.0");
        dataCell = dataRow.createCell(7);
        dataCell.setCellValue("220");
        dataCell = dataRow.createCell(8);
        dataCell.setCellValue("40%");
        dataCell = dataRow.createCell(9);
        dataCell.setCellValue("是");

        dataRow = sheet.createRow(10);
        dataCell = dataRow.createCell(0);
        dataCell.setCellValue("合计");
        dataCell = dataRow.createCell(4);
        dataCell.setCellValue("900.0");
        dataCell = dataRow.createCell(5);
        dataCell.setCellValue("380.0");
        dataCell = dataRow.createCell(6);
        dataCell.setCellValue("520.0");
        dataCell = dataRow.createCell(8);
        dataCell.setCellValue("42%");
        dataCell = dataRow.createCell(9);
        dataCell.setCellValue("否");

        dataRow = sheet.createRow(12);
        dataCell = dataRow.createCell(0);
        dataCell.setCellValue("备注");
        dataCell = dataRow.createCell(1);
        dataCell.setCellValue("1、经费中只要有一个经费不达标，则为不达标");

        dataRow = sheet.createRow(13);
        dataCell = dataRow.createCell(1);
        dataCell.setCellValue("2、提醒：当剩余天数<60天&剩余金额>20&执行率<");

        dataRow = sheet.createRow(14);
        dataCell = dataRow.createCell(1);
        dataCell.setCellValue("部份经费如有三年期使用，再细分为每年可使用的额度");




        FileOutputStream fileOut = new FileOutputStream("data2.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        return new Result(200);
    }
}
