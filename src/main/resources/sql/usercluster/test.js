this._createCalendar = function () {
	var o = this.options;
	var self = this;
	if (this.options.need2BuildConfig === true && this.options.data) {
		this.options.data.resetStatus(this.createDependencePara4Data());
		var data = this.options.data.getData();
		if (data[0].data) {
			if (data[0].data.startDate) {
				var sd = new Date(data[0].data.startDate);
				if (!FR.isInvalidDate(sd)) {
					o.startDate = FR.date2Str(sd, "yyyy-MM-dd");
					this.std = this._createStartDate(o.startDate, o.format, this.viewMode);
				}
			}
			if (data[0].data.endDate) {
				var ed = new Date(data[0].data.endDate);
				if (!FR.isInvalidDate(ed)) {
					o.endDate = FR.date2Str(ed, "yyyy-MM-dd");
					this.edd = this._createEndDate(o.endDate, o.format, this.viewMode);
				}
			}
		}
		this.options.rebuildConfig = false;
	}
	this.datepicker = new FR.DatePicker({
		renderEl: this.$view,
		viewMode: this.viewMode,
		date: FR.str2Date(this.editComp.val(), o.format),
		dateFormat: o.format,
		startDate: this.std,
		endDate: this.edd,
		onDateUpdate: function () {
			if ($(":focus").length === 0) {
				self.editComp.focus();
			}
			self.editComp.val(FR.date2Str(this.getValue(), o.format));
			self.isValidateInput();
			self.fireEvent(FR.Events.AFTEREDIT);
		},
	});
	this.datepicker._loadDateData = function (table, date) {
		if (!date) {
			return;
		}
		var year = date.getFullYear(),
			month = date.getMonth(),
			day = date.getDate();
		var today = new Date(),
			TY = today.getFullYear(),
			TM = today.getMonth(),
			TD = today.getDate();
		this.cache.showYear = year;
		this.cache.showMonth = month;
		var std = this.options.startDate,
			edd = this.options.endDate;
		table.$title.text(Date._MN[month] + ",  " + year);
		var nextDay = new Date(date);
		nextDay.setDate(nextDay.getMonthDays() + 1);
		if ((edd && nextDay > edd) || nextDay.getFullYear() > this.CONSTS.MAXYEAR) {
			table.$nextm.addClass("disabled").removeClass("hover").data("disabled", true);
		} else {
			table.$nextm.removeClass("disabled").data("disabled", false);
		}
		var prevDay = new Date(date);
		prevDay.setDate(0);
		if ((std && prevDay < std) || prevDay.getFullYear() < this.CONSTS.MINYEAR) {
			table.$prevm.addClass("disabled").removeClass("hover").data("disabled", true);
		} else {
			table.$prevm.removeClass("disabled").data("disabled", false);
		}
		date.setDate(1);
		var day1 = (date.getDay() - this.CONSTS.FIRSTDAY) % 7;
		date.setDate(0 - day1);
		date.setDate(date.getDate() + 1);
		var $frow = table.find("tbody").children().eq(0);
		for (var i = 0; i < 6; i++) {
			if (!$frow.length) {
				break;
			}
			var $cell = $frow.children().eq(0);
			$cell.addClass("week  wn").text(date.getWeekNumber());
			var iday;
			for (var j = 0; j < 7; ++j, date.setDate(iday + 1)) {
				$cell = $cell.next();
				$cell.removeClass().data("nav", this.CONSTS.NAV["day"]);
				if (!$cell.length) {
					break;
				}
				iday = date.getDate();
				$cell.text(iday);
				var current_month = date.getMonth() == month;
				if (!current_month || j != 1) {
					$cell.addClass("oday").data("disabled", true);
					continue;
				}
				var disabled = false;
				if ((std != null && std > date) || (edd != null && edd < date)) {
					$cell.addClass("day disabled");
					disabled = true;
				} else {
					$cell.addClass("day");
				}
				$cell.data("disabled", disabled);
				if (!disabled) {
					if (current_month && iday == day) {
						this.cache.selectedDate && this.cache.selectedDate.removeClass("selected");
						$cell.addClass("selected");
						this.cache.selectedDate = $cell;
						this.cache.showDay = iday;
					}
					if (date.getFullYear() == TY && date.getMonth() == TM && iday == TD) {
						$cell.addClass("today");
					}
					var wday = date.getDay();
					if ([0, 6].indexOf(wday) != -1) {
						$cell.addClass("weekend");
					}
				}
			}
			$frow = $frow.next();
		}
	};
	if (FR.Browser.isIE8() && this.$view.css("visibility") == "hidden") {
		this.$view.css("visibility", "visible");
	} else {
		this.$view.show();
	}
	$(document).bind("mousedown", this, this.collapseIf);
	this.modifyPosition();
	var tr = $("tbody>tr", this.datepicker.$datetable);
	for (var i = 0; i < tr.length; i++) {
		var $days = $('td[class!="week wn"]', tr[i]);
		for (var j = 0; j < $days.length; j++) {
			var $day = $($days[j]);
			if (1 != j && !$day.hasClass("oday")) {
				$day.data("disabled", true);
				$day.attr("class", "oday");
			}
		}
	}
};
