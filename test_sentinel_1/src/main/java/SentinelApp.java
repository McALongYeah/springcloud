import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class SentinelApp {
    public static void main(String[] args) {
        //配置流控规则
        initFlowRules();
        //模拟访问
        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性
            try (Entry entry = SphU.entry("HelloWorld")) { //HelloWorld 资源呢的名字
                // 被保护的逻辑
                System.out.println("要进行的逻辑");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>(); //规则列表
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");//此规则对HelloWorld起作用
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//将规则设置为按照每秒请求数来进行限流
        // Set limit QPS to 20.
        rule.setCount(200000); //每秒20个规则
        rules.add(rule);
        FlowRuleManager.loadRules(rules); //添加规则到规则管理器
    }
}
