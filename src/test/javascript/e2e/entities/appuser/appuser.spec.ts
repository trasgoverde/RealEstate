import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AppuserComponentsPage, AppuserDeleteDialog, AppuserUpdatePage } from './appuser.page-object';

const expect = chai.expect;

describe('Appuser e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let appuserComponentsPage: AppuserComponentsPage;
  let appuserUpdatePage: AppuserUpdatePage;
  let appuserDeleteDialog: AppuserDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Appusers', async () => {
    await navBarPage.goToEntity('appuser');
    appuserComponentsPage = new AppuserComponentsPage();
    await browser.wait(ec.visibilityOf(appuserComponentsPage.title), 5000);
    expect(await appuserComponentsPage.getTitle()).to.eq('realEstateApp.appuser.home.title');
    await browser.wait(ec.or(ec.visibilityOf(appuserComponentsPage.entities), ec.visibilityOf(appuserComponentsPage.noResult)), 1000);
  });

  it('should load create Appuser page', async () => {
    await appuserComponentsPage.clickOnCreateButton();
    appuserUpdatePage = new AppuserUpdatePage();
    expect(await appuserUpdatePage.getPageTitle()).to.eq('realEstateApp.appuser.home.createOrEditLabel');
    await appuserUpdatePage.cancel();
  });

  it('should create and save Appusers', async () => {
    const nbButtonsBeforeCreate = await appuserComponentsPage.countDeleteButtons();

    await appuserComponentsPage.clickOnCreateButton();

    await promise.all([
      appuserUpdatePage.setFirstNameInput('firstName'),
      appuserUpdatePage.setLastNameInput('lastName'),
      appuserUpdatePage.setPhoneInput('phone'),
      appuserUpdatePage.setEmailInput('email'),
      appuserUpdatePage.setCifInput('cif'),
      appuserUpdatePage.setLoginInput('login'),
      appuserUpdatePage.companySelectLastOption(),
    ]);

    expect(await appuserUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await appuserUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await appuserUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
    expect(await appuserUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await appuserUpdatePage.getCifInput()).to.eq('cif', 'Expected Cif value to be equals to cif');
    expect(await appuserUpdatePage.getLoginInput()).to.eq('login', 'Expected Login value to be equals to login');

    await appuserUpdatePage.save();
    expect(await appuserUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await appuserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Appuser', async () => {
    const nbButtonsBeforeDelete = await appuserComponentsPage.countDeleteButtons();
    await appuserComponentsPage.clickOnLastDeleteButton();

    appuserDeleteDialog = new AppuserDeleteDialog();
    expect(await appuserDeleteDialog.getDialogTitle()).to.eq('realEstateApp.appuser.delete.question');
    await appuserDeleteDialog.clickOnConfirmButton();

    expect(await appuserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
