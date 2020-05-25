package work.onss.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import work.onss.domain.Store;
import work.onss.exception.ServiceException;
import work.onss.service.StoreService;
import work.onss.vo.Work;

import javax.annotation.Resource;

@Log4j2
@RestController
public class StoreController {

    @Resource
    private StoreService storeService;

    /**
     * @param id 主键
     * @return 店铺信息
     */
    @GetMapping(value = {"store/{id}"})
    public Work<Store> store(@PathVariable String id) {
        Store store = storeService.findById(id, Store.class);
        return Work.success("加载成功", store);
    }

    /**
     * @param x        经度
     * @param y        纬度
     * @param pageable 分页参数
     * @return 店铺分页
     */
    @GetMapping(path = "store/{x}-{y}")
    public Work<Page<Store>> store(@PathVariable(name = "x") Double x, @PathVariable(name = "y") Double y, @PageableDefault(sort = {"insertTime", "updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Store> page = storeService.store(x, y, pageable);
        return Work.success("加载成功", page);
    }

    /**
     * @param x        经度
     * @param y        纬度
     * @param pageable 分页参数
     * @return 店铺分页
     */
    @GetMapping(path = "store/{x}-{y}/{type}")
    public Work<Page<Store>> store(@RequestParam(name = "x") Double x, @RequestParam(name = "y") Double y, @PathVariable Integer type, @PageableDefault(sort = {"insertTime", "updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Store> page = storeService.store(x, y, type, pageable);
        return Work.success("加载成功", page);
    }

    /**
     * @param store 店铺信息
     * @return 店铺信息
     */
    @PostMapping(value = {"store"})
    public Work<Store> store(@RequestBody Store store) throws ServiceException {
        storeService.store(store);
        return Work.success("申请成功,请等待客服审核", store);
    }

}