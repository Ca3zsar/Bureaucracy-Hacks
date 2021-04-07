<template>
    <bar></bar>
</template>

<script>

import Bar from "./bar.js";


export default {
    name: 'StatisticsChart',
    components: { Bar },
    data() {
        return {
            chartOptions: {
                hoverBorderWidth: 20
            },
            chartData: {
                hoverBackgroundColor: "red",
                hoverBorderWidth: 10,
                labels: ["Green", "Red", "Blue"],
                datasets: [
                    {
                        label: "Data One",
                        backgroundColor: ["#41B883", "#E46651", "#00D8FF"],
                        data: [1, 10, 5]
                    }
                ]
            }
        };
    }
}
</script>

