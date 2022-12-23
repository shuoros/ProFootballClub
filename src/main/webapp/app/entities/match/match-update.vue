<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.match.home.createOrEditLabel"
          data-cy="MatchCreateUpdateHeading"
          v-text="$t('proFootballClubApp.match.home.createOrEditLabel')"
        >
          Create or edit a Match
        </h2>
        <div>
          <div class="form-group" v-if="match.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="match.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.date')" for="match-date">Date</label>
            <div class="d-flex">
              <input
                id="match-date"
                data-cy="date"
                type="datetime-local"
                class="form-control"
                name="date"
                :class="{ valid: !$v.match.date.$invalid, invalid: $v.match.date.$invalid }"
                required
                :value="convertDateTimeFromServer($v.match.date.$model)"
                @change="updateInstantField('date', $event)"
              />
            </div>
            <div v-if="$v.match.date.$anyDirty && $v.match.date.$invalid">
              <small class="form-text text-danger" v-if="!$v.match.date.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.match.date.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.weather')" for="match-weather">Weather</label>
            <select
              class="form-control"
              name="weather"
              :class="{ valid: !$v.match.weather.$invalid, invalid: $v.match.weather.$invalid }"
              v-model="$v.match.weather.$model"
              id="match-weather"
              data-cy="weather"
              required
            >
              <option
                v-for="weather in weatherValues"
                :key="weather"
                v-bind:value="weather"
                v-bind:label="$t('proFootballClubApp.Weather.' + weather)"
              >
                {{ weather }}
              </option>
            </select>
            <div v-if="$v.match.weather.$anyDirty && $v.match.weather.$invalid">
              <small class="form-text text-danger" v-if="!$v.match.weather.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.hostGoals')" for="match-hostGoals">Host Goals</label>
            <input
              type="number"
              class="form-control"
              name="hostGoals"
              id="match-hostGoals"
              data-cy="hostGoals"
              :class="{ valid: !$v.match.hostGoals.$invalid, invalid: $v.match.hostGoals.$invalid }"
              v-model.number="$v.match.hostGoals.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.guestGoals')" for="match-guestGoals">Guest Goals</label>
            <input
              type="number"
              class="form-control"
              name="guestGoals"
              id="match-guestGoals"
              data-cy="guestGoals"
              :class="{ valid: !$v.match.guestGoals.$invalid, invalid: $v.match.guestGoals.$invalid }"
              v-model.number="$v.match.guestGoals.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.events')" for="match-events">Events</label>
            <input
              type="text"
              class="form-control"
              name="events"
              id="match-events"
              data-cy="events"
              :class="{ valid: !$v.match.events.$invalid, invalid: $v.match.events.$invalid }"
              v-model="$v.match.events.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.type')" for="match-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.match.type.$invalid, invalid: $v.match.type.$invalid }"
              v-model="$v.match.type.$model"
              id="match-type"
              data-cy="type"
            >
              <option
                v-for="matchType in matchTypeValues"
                :key="matchType"
                v-bind:value="matchType"
                v-bind:label="$t('proFootballClubApp.MatchType.' + matchType)"
              >
                {{ matchType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.host')" for="match-host">Host</label>
            <select class="form-control" id="match-host" data-cy="host" name="host" v-model="match.host">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.host && teamOption.id === match.host.id ? match.host : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.guest')" for="match-guest">Guest</label>
            <select class="form-control" id="match-guest" data-cy="guest" name="guest" v-model="match.guest">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.guest && teamOption.id === match.guest.id ? match.guest : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.hostComposition')" for="match-hostComposition"
              >Host Composition</label
            >
            <select
              class="form-control"
              id="match-hostComposition"
              data-cy="hostComposition"
              name="hostComposition"
              v-model="match.hostComposition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  match.hostComposition && compositionOption.id === match.hostComposition.id ? match.hostComposition : compositionOption
                "
                v-for="compositionOption in compositions"
                :key="compositionOption.id"
              >
                {{ compositionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.guestComposition')" for="match-guestComposition"
              >Guest Composition</label
            >
            <select
              class="form-control"
              id="match-guestComposition"
              data-cy="guestComposition"
              name="guestComposition"
              v-model="match.guestComposition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  match.guestComposition && compositionOption.id === match.guestComposition.id ? match.guestComposition : compositionOption
                "
                v-for="compositionOption in compositions"
                :key="compositionOption.id"
              >
                {{ compositionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.bestPlayer')" for="match-bestPlayer">Best Player</label>
            <select class="form-control" id="match-bestPlayer" data-cy="bestPlayer" name="bestPlayer" v-model="match.bestPlayer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.bestPlayer && playerOption.id === match.bestPlayer.id ? match.bestPlayer : playerOption"
                v-for="playerOption in players"
                :key="playerOption.id"
              >
                {{ playerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.league')" for="match-league">League</label>
            <select class="form-control" id="match-league" data-cy="league" name="league" v-model="match.league">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.league && leagueOption.id === match.league.id ? match.league : leagueOption"
                v-for="leagueOption in leagues"
                :key="leagueOption.id"
              >
                {{ leagueOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.match.cupBoard')" for="match-cupBoard">Cup Board</label>
            <select class="form-control" id="match-cupBoard" data-cy="cupBoard" name="cupBoard" v-model="match.cupBoard">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.cupBoard && cupBoardOption.id === match.cupBoard.id ? match.cupBoard : cupBoardOption"
                v-for="cupBoardOption in cupBoards"
                :key="cupBoardOption.id"
              >
                {{ cupBoardOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.match.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./match-update.component.ts"></script>
