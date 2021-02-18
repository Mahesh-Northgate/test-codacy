import groovy.json.JsonSlurper


String call() { 
        String summary = ' \u2022 ';
        int maxDisplayMessages = 5
        try {
            String directory = "C:/office"
            int totalScenariosCount = 0
            def totalScenarios = []
            def failedScenarios = []

            File file = new File(directory)
            def fileList = file.list()           

            fileList.each {
                if(it.endsWith('.json')) { parseCucumberReportFile(directory + '/' + it, totalScenarios, failedScenarios) }
            }


            if (!failedScenarios.isEmpty()) {
                totalScenarios.each { totalScenariosCount += it }
                summary += 'Out of ' + totalScenariosCount + ' scenarios ' + failedScenarios.size() + ' have failed'
                summary += '\n\n Failed Scenarios:'

                int i = 1
                for (String scr : failedScenarios) {
                    summary += '\n \u2022 ' + scr
                    i++
                    if (i > maxDisplayMessages) {
                        break
                    }
                }

                if (i > maxDisplayMessages) {
                    summary += "\n \u2022 _${slackLink("\u2026${failedScenarios.size() - maxDisplayMessages} more", "${env.BUILD_URL}testReport")}_"
                }
            } else {
                summary = null
            }


        } catch (Exception e) {
            println(e.getMessage())
        }
       return summary
}


void parseCucumberReportFile(String file, def totalScenarios, def failedScenarios){
    def slurper = new JsonSlurper()
    def reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))
    def dataList = slurper.parse(reader)
                    
    dataList.each {
        def elements = it['elements'] as List
            if (elements != null && !elements.isEmpty()) {
            totalScenarios.add(elements.size())
                
            for (def element : elements) {
                String scenario = element['name']
                def steps = element['steps'] as List
                
                if (steps != null && !steps.isEmpty()) {
                    def step = steps.last()
                    def result = step['result']
                    String status = result['status']
                
                    if (status != 'passed') {
                        failedScenarios.add(scenario)
                    }
                }
            }
        }
    }
}


String slackLink(def text, def target) {
	"<${slackEncode target}|${slackEncode text}>"
}

/**
 * Escape any Slack formatting characters which can be escaped. This is a best effort, so messages
 * may still look a bit weird in some circumstances (e.g. underscores in job names), but at least
 * links should still work properly.
 */

String slackEncode(def str) {
	if (str == null) {
		return ''
	}
	str = str.toString()
	// Not fully HTML-encoded, as specified by
	//  https://api.slack.com/reference/surfaces/formatting#escaping
	str = str.replace '&', '&amp;'
	str = str.replace '<', '&lt;'
	str = str.replace '>', '&gt;'
	str
}
