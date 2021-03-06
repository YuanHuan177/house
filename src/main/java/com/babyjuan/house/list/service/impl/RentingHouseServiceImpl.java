package com.babyjuan.house.list.service.impl;

import com.babyjuan.house.common.EasyUIDataGridResult;
import com.babyjuan.house.common.constant.Constant;
import com.babyjuan.house.common.enums.RecordStatus;
import com.babyjuan.house.list.service.dto.RentingHouseDto;
import com.babyjuan.house.repository.CommunityMapper;
import com.babyjuan.house.repository.RentingHouseMapper;
import com.babyjuan.house.repository.entity.Community;
import com.babyjuan.house.repository.entity.CommunityExample;
import com.babyjuan.house.repository.entity.RentingHouse;
import com.babyjuan.house.repository.entity.RentingHouseExample;
import com.babyjuan.house.repository.entity.RentingHouseExample.Criteria;
import com.babyjuan.house.list.service.RentingHouseService;
import com.babyjuan.house.pusher.PusherConst;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: hjg
 * @Date: Create in 2018/6/21 17:37
 * @Description:
 */
@Service
public class RentingHouseServiceImpl implements RentingHouseService {

    @Autowired
    private RentingHouseMapper rentingHouseMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private PusherConst pusherConst;

    @Override
    public List<RentingHouse> getLatestFavourateHouseList(String communityName, Date lastPushTime) {
        CommunityExample communityExample = new CommunityExample();
        CommunityExample.Criteria communityCriteria = communityExample.createCriteria();
        communityCriteria.andStatusNotEqualTo(RecordStatus.EXPIRED.getStatus());
        communityCriteria.andCommunityNameLike(modifyName(communityName));
        List<Community> communityList = communityMapper.selectByExample(communityExample);

        if (communityList.isEmpty()) {
            return new ArrayList<>();
        } else {
            RentingHouseExample houseExample = new RentingHouseExample();
            Criteria houseCriteria = houseExample.createCriteria();
            houseCriteria.andStatusNotEqualTo(RecordStatus.EXPIRED.getStatus());
            houseCriteria.andCommunityInfoIdIn(getCommunityInfoId(communityList));
            houseCriteria.andFromTimeGreaterThan(lastPushTime);
            List<RentingHouse> list = rentingHouseMapper.selectByExample(houseExample);
            return list;
        }
    }

    @Override
    public EasyUIDataGridResult getFavourateHouseList(int page, int rows) {
        List<RentingHouse> houseList = new ArrayList<>();
        for (String communityName : pusherConst.getPushedCommunities()) {
            houseList.addAll(getLatestFavourateHouseList(communityName, Constant.LONG_LONG_AGO));
        }

        List<RentingHouseDto> houseDtoList = new ArrayList<>();
        for (RentingHouse house : houseList) {
            Community community = communityMapper.selectByPrimaryKey(house.getCommunityInfoId());
            String communityName = community.getCommunityName();
            RentingHouseDto houseDto = getRentingHouseDto(house, communityName);
            houseDtoList.add(houseDto);
        }

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(houseDtoList.size());
        result.setRows(houseDtoList);

        return result;
    }

    @Override
    public EasyUIDataGridResult getRentingHouseList(int page, int rows) {
        //使用pagehelper插件分页
        PageHelper.startPage(page, rows);

        RentingHouseExample example = new RentingHouseExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo(RecordStatus.EXPIRED.getStatus());
        List<RentingHouse> houseList = rentingHouseMapper.selectByExample(example);

        List<RentingHouseDto> houseDtoList = new ArrayList<>();
        for (RentingHouse house : houseList) {
            Community community = communityMapper.selectByPrimaryKey(house.getCommunityInfoId());
            String communityName = community.getCommunityName();

            RentingHouseDto houseDto = getRentingHouseDto(house, communityName);
            houseDtoList.add(houseDto);
        }

        PageInfo<RentingHouse> pageInfo = new PageInfo<>(houseList);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(houseDtoList);

        return result;
    }

    private RentingHouseDto getRentingHouseDto(RentingHouse house, String communityName) {
        RentingHouseDto houseDto = new RentingHouseDto();
        houseDto.setTitle(house.getTitle());
        houseDto.setHallNum(house.getHallNum());
        houseDto.setBedroomNum(house.getBedroomNum());
        houseDto.setPriceTotal(house.getPriceTotal());
        houseDto.setRentArea(house.getRentArea());
        houseDto.setCommunity(communityName);
        houseDto.setFromTime(house.getFromTime());
        return houseDto;
    }

    private List<Long> getCommunityInfoId(List<Community> communityList) {
        List<Long> list = new ArrayList<>();
        for (Community community : communityList) {
            list.add(community.getInfoId());
        }
        return list;
    }

    private String modifyName(String name) {
        return "%" + name + "%";
    }
}
